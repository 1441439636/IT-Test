package gt.maxzhao.ittest.app.login.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maxzhao
 */
@RestController
@RequestMapping("/app")
public class AppLoginController {
    @RequestMapping(name = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(Object o) {

        return "";
    }
}
