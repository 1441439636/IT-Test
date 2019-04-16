package start.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 自定义资源文件
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-configuration-processor</artifactId>
 * <optional>true</optional>
 * </dependency>
 *
 * @author maxzhao
 */
@Configuration
@ConfigurationProperties(prefix = "gt.maxzhao")
@PropertySource(value = "classpath:local-resource.properties")
@Data
public class LocalResources {
    private String name;
    private Integer age;
}
