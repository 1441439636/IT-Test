package start.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Demo class
 *
 * @author maxzhao
 * @date 2019/01/14
 */
@Configuration
//@EnableAutoConfiguration //自动加载配置信息
//@EnableAspectJAutoProxy
//@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories("gt.maxzhao.**.repo")
@EntityScan({"gt.maxzhao.**.model"})
@ComponentScan({"gt.maxzhao.**.aspect", "gt.maxzhao.**.utils"})
@ComponentScan({"gt.maxzhao.**.api", "gt.maxzhao.**.service.**", "gt.maxzhao.**.dao"})
@PropertySource(value = {"classpath:*.properties","classpath:*.yml"}, ignoreResourceNotFound = true, encoding = "utf-8")
public class StartApplication {
    public StartApplication() {
        super();
    }


}