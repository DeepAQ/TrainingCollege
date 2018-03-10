package cn.imaq.trainingcollege.config;

import cn.imaq.autumn.core.annotation.BeanFactory;
import cn.imaq.autumn.core.annotation.Component;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

@Component
public class MyBatisConfig {
    @BeanFactory
    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        return new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
    }
}
