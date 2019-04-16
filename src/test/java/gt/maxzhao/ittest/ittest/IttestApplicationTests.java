package gt.maxzhao.ittest.ittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import start.IttestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IttestApplication.class)
public class IttestApplicationTests {
    private enum MyTest {
        longFactory;
        private Long a;

        private MyTest() {
            a = 10l;
            System.out.println("----------------");
        }

        public long getInstance() {
            return a;
        }
    }

    @Test
    public void contextLoads() {
        System.out.println(MyTest.longFactory.getInstance());
        System.out.println(MyTest.longFactory.getInstance());
    }


}

