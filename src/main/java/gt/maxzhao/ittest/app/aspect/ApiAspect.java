package gt.maxzhao.ittest.app.aspect;

import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author maxzhao
 */
@Aspect
@Component
public class ApiAspect {
    private boolean saveRequestInfo;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ApiAspect() {
        super();
    }

    private final String API_INFO_CUT = "execution(public * gt.maxzhao..api..*.*(..))";

    /**
     * 定义切入点，切入点为com.example.aop下的所有函数
     */
    /*@Pointcut( "execution(public * gt.maxzhao..api..*.*(..))")
    public void ApiLog() {
    }

    */

    /**
     * 前置通知：在连接点之前执行的通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before(value = API_INFO_CUT)
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        logger.info("前置通知");
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        //AOP代理类的信息
        joinPoint.getThis();
        //代理的目标对象
        joinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        logger.info("代理的是哪一个方法" + signature.getName());
        //AOP代理类的名字
        logger.info("AOP代理类的名字" + signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
        signature.getDeclaringType();
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //如果要获取Session信息的话，可以这样写：
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        //获取请求参数
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, String> parameterMap = Maps.newHashMap();
        while (enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter, request.getParameter(parameter));
        }
        String str = JSONObject.valueToString(parameterMap);
        if (obj.length > 0) {
            logger.info("请求的参数信息为：" + str);
        }

        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = API_INFO_CUT)
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }

    /**
     * 后置返回通知
     * 这里需要注意的是:
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning：限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，
     * 对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     *
     * @param joinPoint
     * @param keys
     */
    @AfterReturning(pointcut = API_INFO_CUT, returning = "keys")
    public void doAfterReturningAdvice1(JoinPoint joinPoint, Object keys) {
        logger.info("第一个后置返回通知的返回值：" + keys);
    }

    @AfterReturning(pointcut = API_INFO_CUT, returning = "keys", argNames = "keys")
    public void doAfterReturningAdvice2(String keys) {
        logger.info("第二个后置返回通知的返回值：" + keys);
    }

    /**
     * 后置异常通知
     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     * throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     * 对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     *
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = API_INFO_CUT, throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        //目标方法名：
        logger.info(joinPoint.getSignature().getName());
        if (exception instanceof NullPointerException) {
            logger.info("发生了空指针异常!!!!!");
        }
    }

    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     *
     * @param joinPoint
     */
    @After(value = API_INFO_CUT)
    public void doAfterAdvice(JoinPoint joinPoint) {
        logger.info("后置最终通知执行了!!!!");
    }

    /**
     * 环绕通知：
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around(value = API_INFO_CUT)
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        logger.info("环绕通知的目标方法名：" + proceedingJoinPoint.getSignature().getName());
        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
