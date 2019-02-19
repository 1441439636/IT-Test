package gt.maxzhao.ittest.app.user.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maxzhao
 */
@RestController
@RequestMapping("appUserController")
public class AppUserController {
    @RequestMapping("regist")
    public String regist() {
        System.out.println();
        return "Hello";
    }
}
