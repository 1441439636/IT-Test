package gt.maxzhao.ittest.ittest.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.SpringRunner;
import start.IttestApplication;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IttestApplication.class)
public class RedisTest {


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisTemplate() {
        String prefix = "maxzhao:redisTemplate:";
        HashOperations hashOperations = redisTemplate.opsForHash();
        ValueOperations valueOps = redisTemplate.opsForValue();
        ListOperations listOps = redisTemplate.opsForList();
        SetOperations setOps = redisTemplate.opsForSet();
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        GeoOperations geoOperations = redisTemplate.opsForGeo();
        ClusterOperations clusterOperations = redisTemplate.opsForCluster();
        Map map = Arrays.stream(new String[]{"a", "B"}).collect(Collectors.toMap(Function.identity(), Function.identity()));
        hashOperations.putAll(prefix + "hash", map);
    }

    @Test
    public void testStringRedisTemplate() {
        String prefix = "maxzhao:stringRedisTemplate:";
        HashOperations hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.putAll(prefix + "hash", Arrays.stream(new String[]{"a", "b"}).collect(Collectors.toMap(Function.identity(), Function.identity())));
        hashOperations.putAll(prefix + "hash", Arrays.stream(new String[]{"c", "d"}).collect(Collectors.toMap(Function.identity(), Function.identity())));
        hashOperations.putAll(prefix + "hash", Arrays.stream(new String[]{"e", "f"}).collect(Collectors.toMap(Function.identity(), Function.identity())));
        hashOperations.put(prefix + "hash", "max", "maxvalue");// a b c d e f max
        hashOperations.get(prefix + "hash", "max");// maxvalue
        hashOperations.delete(prefix + "hash", "f");// return 1  database:a b c d e max
        hashOperations.delete(prefix + "hash", "c", "d");// return 2  database:a b e max
        hashOperations.hasKey(prefix + "hash", "max");//return true
        hashOperations.values(prefix + "hash");// return map object
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(prefix + "value", "value");
        valueOperations.set(prefix + "value", "valueTest");//value 被覆盖
        valueOperations.get(prefix + "value");// return valueTest

        ListOperations listOps = stringRedisTemplate.opsForList();
//        listOps.remove(prefix + "list", listOps.size(prefix + "list"), 1);
        listOps.leftPush(prefix + "list", "A");
        listOps.leftPush(prefix + "list", "B");
        listOps.rightPush(prefix + "list", "C", "D");//只有 1：B  2：A
        listOps.leftPush(prefix + "list", "C");
        listOps.leftPush(prefix + "list", "D");//return 3  1:D 2:C 3:A
        listOps.range(prefix + "list", 0, listOps.size(prefix + "list"));//return 3  0:D 1:C 2:A   list下标从0开始
        listOps.leftPop(prefix + "list");//只有   1：A 返回的为B
        listOps.leftPush(prefix + "list2", "A");
        listOps.leftPush(prefix + "list2", "B");//只有 1：B  2：A
        listOps.rightPush(prefix + "list2", "C");//只有 1：B  2：A 3 C

        // set 是无序的，所有pop等获取value的操作，得到结果可能不同
        SetOperations setOps = stringRedisTemplate.opsForSet();
        setOps.add(prefix + "set", "A");//return 1
        setOps.add(prefix + "set", "A");//return 0
        setOps.add(prefix + "set", "B");//return 1
        setOps.difference(prefix + "set", "A");//return HashSet  A,B
        setOps.isMember(prefix + "set", "A");//return true
        setOps.isMember(prefix + "set", "C");//return false
        setOps.members(prefix + "set");//return HashSet  A,B
        setOps.pop(prefix + "set");// 出序列并删除 1个
        setOps.add(prefix + "set", "A", "B", "C", "D", "E");//return 5
        setOps.pop(prefix + "set", 2);// 出序列并删除 2个
        setOps.add(prefix + "set", "A", "B", "C", "D", "E");
        setOps.move(prefix + "set", "D", "A");//return  true  database=BCE
        // 把当前key=set的c值，move到 key=set1
        setOps.move(prefix + "set", "C", prefix + "set1");//return  true
        setOps.remove(prefix + "set", "C", "D");//删除
        // 有序的set
        ZSetOperations zSetOps = stringRedisTemplate.opsForZSet();

        GeoOperations geoOperations = stringRedisTemplate.opsForGeo();
        // 集群操作， 只有jedis 和 lettuce 支持Redis Cluster。
        ClusterOperations clusterOperations = stringRedisTemplate.opsForCluster();

    }

}
