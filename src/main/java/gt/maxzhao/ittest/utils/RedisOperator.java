package gt.maxzhao.ittest.utils;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author maxzhao
 */
public class RedisOperator {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


}
