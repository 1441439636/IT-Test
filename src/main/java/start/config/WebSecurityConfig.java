package start.config;

import gt.maxzhao.ittest.app.user.repo.AppUserRepository;
import gt.maxzhao.ittest.app.user.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import start.IttestApplication;

import javax.annotation.Resource;

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
                        .loginPage("/login").permitAll()
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
                        // 自定义的登录接口,默认为 this.loginPage , 在抽象类AbstractAuthenticationFilterConfigurer
//                        .loginProcessingUrl("")

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
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //user Details Service验证
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//        auth
//                .userDetailsService(userDetailsServiceImpl())
//                .passwordEncoder(passwordEncoderImpl());
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)  throws Exception {
        auth.userDetailsService(buildUserDetailsService());
    }
    @Bean
    UserDetailsService buildUserDetailsService() {
        return username -> {
            AppUser account = appUserRepository.findByLoginAccount(username);
            User user = new User(account.getLoginAccount(), account.getLoginPasswd(),
                    true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER", "write"));
            return user;
        };
    }
}
