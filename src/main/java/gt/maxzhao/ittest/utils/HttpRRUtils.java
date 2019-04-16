package gt.maxzhao.ittest.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maxzhao
 */
public class HttpRRUtils {
    /**
     * 判断是否为AJAX请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
