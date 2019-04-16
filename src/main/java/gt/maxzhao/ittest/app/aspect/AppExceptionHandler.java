package gt.maxzhao.ittest.app.aspect;

import gt.maxzhao.ittest.utils.HttpRRUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 *
 * @author maxzhao
 */
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(HttpServletRequest request,
                               HttpServletResponse response, Exception e) throws Exception {
        e.printStackTrace();
        ModelAndView mav = new ModelAndView();
        if (HttpRRUtils.isAjax(request)) {
            return response;
        } else {

            mav.addObject("url", request.getRequestURL());
            // server.error.path=/error
            mav.setViewName("/error");
            return mav;
        }
    }
}
