package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {
    @Insert("INSERT INTO `students` (`email`, `pwdhash`, `name`, `status`) VALUES (#{email}, #{pwdHash}, #{name}, #{status})")
    void insert(Student po);

    @Select("SELECT * FROM `students` WHERE `email` = #{email}")
    Student getByEmail(String email);
}
