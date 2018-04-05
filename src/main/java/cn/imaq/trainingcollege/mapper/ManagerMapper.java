package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Manager;
import cn.imaq.trainingcollege.domain.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManagerMapper {
    @Insert("INSERT INTO `managers` (`username`, `pwd_hash`) VALUES (#{username}, #{pwdHash})")
    @Options(useGeneratedKeys = true)
    void insert(Manager po);

    @Select("SELECT * FROM `managers` WHERE `username` = #{username}")
    Manager getByUsername(String username);

    @Select("SELECT * FROM `managers` WHERE `id` = #{managerId}")
    Manager getById(Integer managerId);
}
