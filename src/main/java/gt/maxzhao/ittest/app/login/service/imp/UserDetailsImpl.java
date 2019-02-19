package gt.maxzhao.ittest.app.login.service.imp;

import gt.maxzhao.ittest.app.user.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 提供核心用户信息。
 * 出于安全目的，Spring Security不直接使用实现。它们只是存储用户信息，这些信息稍后封装到身份验证对象中。这允许将非安全相关的用户信息(如电子邮件地址、电话号码等)存储在一个方便的位置。
 * 具体实现必须特别注意，以确保每个方法的非空契约都得到了执行。有关参考实现(您可能希望在代码中对其进行扩展或使用)，请参见User。
 * @author maxzhao
 */
public class UserDetailsImpl implements UserDetails {
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
