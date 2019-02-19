package start.config;

import gt.maxzhao.ittest.app.user.model.AppUser;
import gt.maxzhao.ittest.app.user.repo.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import start.IttestApplication;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * web
 * EnableGlobalMethodSecurity 启用方法级的权限认证
 *
 * @author maxzhao
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(IttestApplication.class);

    @Resource(name = "appUserRepository")
    private AppUserRepository appUserRepository;

    /**
     * 自定义用户认证逻辑
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        this.logger.debug("Using start.config.WebSecurityConfig configure(HttpSecurity). ");
        (
                (
                        (HttpSecurity)
                                (
                                        (ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http
                                                .authorizeRequests()// 定义哪些URL需要被保护、哪些不需要被保护
                                                // 设置所有人都可以访问home页面
                                                .antMatchers("/", "/home").permitAll()
                                                .anyRequest()// 任何请求,登录后可以访问
                                )
                                        .authenticated()
                                        .and()
                )
                        .formLogin() // 定义当需要用户登录时候，转到的登录页面。
                        // 默认/login 在抽象类AbstractAuthenticationFilterConfigurer
                        .loginPage("/login")
                        // 自定义的登录接口,默认为 this.loginPage , 在抽象类AbstractAuthenticationFilterConfigurer
//                        .loginProcessingUrl("/app/login")
                        .permitAll()
                        // 默认username 在类 UsernamePasswordAuthenticationFilter,FormLoginConfigurer初始化方法也设置了默认值
                        .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                        // 默认password 在类 UsernamePasswordAuthenticationFilter,FormLoginConfigurer初始化方法也设置了默认值
                        .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                        // 在抽象类AbstractAuthenticationFilterConfigurer
//                        .successForwardUrl("")
                        // 在抽象类AbstractAuthenticationFilterConfigurer
//                        .failureForwardUrl("")
                        // failureForwardUrl没有设置时,this.failureUrl(this.loginPage + "?error"); , 在抽象类AbstractAuthenticationFilterConfigurer
//                        .failureUrl("")
                        .permitAll()

                        .and()
        )
                .httpBasic();
        System.out.println();
//        http
//                .and()
//                .csrf().disable();          // 关闭csrf防护
//        http.logout()
//                .logoutUrl("/my/logout")
//                .logoutSuccessUrl("/my/index");
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .invalidateHttpSession(true)
//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear);
    }

    /**
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    user Details Service验证
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
        auth
                .userDetailsService(buildUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }
    // out of date ,use protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(buildUserDetailsService());
//    }

    UserDetailsService buildUserDetailsService() {
        return username -> {
            AppUser account = appUserRepository.findByLoginAccount(username);
            User user = new User(account.getLoginAccount(), account.getLoginPasswd(),
                    true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER", "write"));
            return user;
        };
    }


    private PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            /**
             * jiami
             * @param rawPassword
             * @return
             */
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            /**
             * yanzheng
             * @param rawPassword
             * @param encodedPassword
             * @return
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
    }
}

/**
 * @author maxzhao
 */
class PasswordEncoderImpl implements PasswordEncoder {
    private Pattern BCRYPT_PATTERN;
    private Logger logger;
    private final int strength;
    private final SecureRandom random;

    /**
     * 构造函数用于设置不同的加密过程
     */
    public PasswordEncoderImpl() {
        this(-1);
    }

    public PasswordEncoderImpl(int strength) {
        this(strength, null);
    }

    public PasswordEncoderImpl(int strength, SecureRandom random) {
        this.BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        this.logger = LoggerFactory.getLogger(this.getClass());
        if (strength == -1 || strength >= 4 && strength <= 31) {
            this.strength = strength;
            this.random = random;
        } else {
            throw new IllegalArgumentException("Bad strength");
        }
    }

    /**
     * 对原始密码进行编码。通常，一个好的编码算法应用SHA-1或更大的哈希值和一个8字节或更大的随机生成的salt。
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or greater hash combined with an 8-byte or greater randomly generated salt.
     *
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
       /* String salt;
        if (this.strength > 0) {
            if (this.random != null) {
                salt = BCrypt.gensalt(this.strength, this.random);
            } else {
                salt = BCrypt.gensalt(this.strength);
            }
        } else {
            salt = BCrypt.gensalt();
        }

        return BCrypt.hashpw(rawPassword.toString(), salt);*/
        return rawPassword.toString();
    }

    /**
     * 验证从存储中获得的已编码密码在经过编码后是否与提交的原始密码匹配。
     * 如果密码匹配，返回true;如果密码不匹配，返回false。存储的密码本身永远不会被解码。
     *
     * @param rawPassword     the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        /*if (encodedPassword != null && encodedPassword.length() != 0) {
            if (!this.BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
                this.logger.warn("Encoded password does not look like BCrypt");
                return false;
            } else {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        } else {
            this.logger.warn("Empty encoded password");
            return false;
        }*/
        return rawPassword.equals(encodedPassword);
    }

    /**
     * 如果为了更好的安全性，应该再次对已编码的密码进行编码，则返回true，否则为false。
     *
     * @param encodedPassword the encoded password to check
     * @return Returns true if the encoded password should be encoded again for better security, else false. The default implementation always returns false.
     */
    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
/**
 * 加载特定于用户的数据的核心接口。
 * 它作为用户DAO在整个框架中使用，是DaoAuthenticationProvider使用的策略。
 * 该接口只需要一个只读方法，这简化了对新数据访问策略的支持。
 *
 * @author maxzhao
 */
 class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * 根据用户名定位用户。
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = new AppUser();
        appUser.setLoginAccount(username);
        appUser.setLoginPasswd("1");
        UserDetails userDetails = new UserDetailsImpl(appUser);
        return userDetails;
    }
}
/**
 * 提供核心用户信息。
 * 出于安全目的，Spring Security不直接使用实现。它们只是存储用户信息，这些信息稍后封装到身份验证对象中。这允许将非安全相关的用户信息(如电子邮件地址、电话号码等)存储在一个方便的位置。
 * 具体实现必须特别注意，以确保每个方法的非空契约都得到了执行。有关参考实现(您可能希望在代码中对其进行扩展或使用)，请参见User。
 * @author maxzhao
 */
class UserDetailsImpl implements UserDetails {
    private AppUser appUser;

    public UserDetailsImpl(AppUser appUser) {
        this.appUser = appUser;
    }

    /**
     * 返回用户所有角色的封装，一个Role对应一个GrantedAuthority
     *
     * @return 返回授予用户的权限。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String username = this.getUsername();
        if (username != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(username);
            authorities.add(authority);
        }
        return authorities;
    }

    /**
     * 返回用于验证用户身份的密码。
     * @return Returns the password used to authenticate the user.
     */
    @Override
    public String getPassword() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getUsername() {
        return null;
    }

    /**
     * 判断账号是否已经过期，默认没有过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 判断账号是否被锁定，默认没有锁定
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 判断信用凭证是否过期，默认没有过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 判断账号是否可用，默认可用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return false;
    }
}
