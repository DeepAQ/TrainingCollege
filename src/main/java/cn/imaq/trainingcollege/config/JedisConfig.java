package cn.imaq.trainingcollege.config;

import cn.imaq.autumn.core.annotation.BeanFactory;
import cn.imaq.autumn.core.annotation.Component;
import redis.clients.jedis.Jedis;

@Component
public class JedisConfig {
    @BeanFactory
    public static Jedis getJedis() {
        return new Jedis(Sensitive.JEDIS_HOST, Sensitive.JEDIS_PORT);
    }
}
