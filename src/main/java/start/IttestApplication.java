package start;

import gt.maxzhao.ittest.app.user.repo.AppUserRepository;
import gt.maxzhao.ittest.app.user.model.AppUser;
import gt.maxzhao.ittest.utils.SprintBootStartUtils;
//import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

//import javax.jms.Queue;

/**
 * Demo class
 *
 * @author maxzhao
 * @date 2019/01/14
 */
@SpringBootApplication
@EnableCaching
public class IttestApplication {
    private static Logger logger = LoggerFactory.getLogger(IttestApplication.class);

    public static void main(String[] args) {
        logger.info("spring boot start");
        SprintBootStartUtils.LoadBasePackages(IttestApplication.class.getName());
//        SpringApplication.run(IttestApplication.class, args);

        //region 自定义启动可以添加很多自己的配置,初始化类，可以添加自己的ResourceLoader
        SpringApplication application = new SpringApplication(IttestApplication.class);
        application.run(args);
        //endregion
    }
    //region 初始化数据

    /**
     * @param appUserRepository
     * @return
     */
    @Bean
    CommandLineRunner init(AppUserRepository appUserRepository) {
        return (args) -> {
            appUserRepository.deleteAll();
            Arrays.stream("ntop,lilei,hanmeimei".split(",")).forEach(name -> {
                appUserRepository.save(new AppUser(name, new BCryptPasswordEncoder().encode("1")));
            });
        };
    }

    /**
     * @param appUserRepository
     * @return
     */
    @Bean
    CommandLineRunner init2(AppUserRepository appUserRepository) {
        return (args) -> {
            Arrays.stream("ntop,lilei,hanmeimei".split(",")).forEach(name -> {
//                appUserRepository.save(new AppUser(name, "1"));
            });
        };
    }
    //endregion
//    @Bean
//    public Queue queue() {
//        return new ActiveMQQueue("maxzhao.queue");
//    }
}

