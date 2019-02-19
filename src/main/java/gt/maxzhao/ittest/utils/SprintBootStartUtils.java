package gt.maxzhao.ittest.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author maxzhao
 */
public class SprintBootStartUtils {
    @Value("${springboot.basepackage}")
    private static final String BASE_PACKAGE_DIR = "gt.maxzhao.**.config";
    private static final String BASE_CONFIG_DIR = "**.start.**.config";

    public static void LoadBasePackages(String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(clazz, className + ":class not found！");
        SpringBootApplication springBootApplication = (SpringBootApplication) clazz.getAnnotation(SpringBootApplication.class);
        String[] packages = springBootApplication.scanBasePackages();
        boolean basePackgeFlag = Arrays.stream(packages).filter(item -> item.equals(BASE_PACKAGE_DIR)).toArray().length > 0;
        boolean baseConfigFlag = Arrays.stream(packages).filter(item -> item.equals(BASE_CONFIG_DIR)).toArray().length > 0;
        if (!basePackgeFlag || !baseConfigFlag) {
            try {
                InvocationHandler invocationHandler = Proxy.getInvocationHandler(springBootApplication);
                Field value = invocationHandler.getClass().getDeclaredField("memberValues");
                value.setAccessible(true);
                Map<String, Object> memberValues = (Map<String, Object>) value.get(invocationHandler);
                String[] packagesVal = new String[packages.length + ((basePackgeFlag ^ baseConfigFlag) ? 1 : 2)];
                System.arraycopy(packages, 0, packagesVal, 0, packages.length);
                if (basePackgeFlag ^ baseConfigFlag) {
                    packagesVal[packagesVal.length - 1] = basePackgeFlag ? BASE_CONFIG_DIR : BASE_PACKAGE_DIR;
                } else {
                    packagesVal[packagesVal.length - 1] = BASE_PACKAGE_DIR;
                    packagesVal[packagesVal.length - 2] = BASE_CONFIG_DIR;
                }
                memberValues.put("scanBasePackages", packagesVal);
                System.out.println("改变后：" + Arrays.toString(springBootApplication.scanBasePackages()));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
