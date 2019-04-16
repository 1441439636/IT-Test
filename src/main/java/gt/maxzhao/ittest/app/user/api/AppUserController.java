package gt.maxzhao.ittest.app.user.api;

import gt.maxzhao.ittest.app.user.model.AppUser;
import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import start.config.LocalResources;

import javax.annotation.Resource;

/**
 * @author maxzhao
 */
@RestController
@RequestMapping("appUserController")
public class AppUserController {
    @Resource(name = "localResources")
    private LocalResources localResources;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @RequestMapping("regist")
    public Object regist() {
        System.out.println();
        localResources.getName();
        AppUser appUser = new AppUser("111", "222");
        return appUser;
    }
}
