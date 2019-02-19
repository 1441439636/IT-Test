package gt.maxzhao.ittest.app.login.service.imp;

import gt.maxzhao.ittest.app.user.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 加载特定于用户的数据的核心接口。
 * 它作为用户DAO在整个框架中使用，是DaoAuthenticationProvider使用的策略。
 * 该接口只需要一个只读方法，这简化了对新数据访问策略的支持。
 *
 * @author maxzhao
 */
public class UserDetailsServiceImpl implements UserDetailsService {
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
