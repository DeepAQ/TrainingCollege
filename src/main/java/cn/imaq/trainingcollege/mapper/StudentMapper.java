package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {
    @Insert("INSERT INTO `students` (`email`, `pwd_hash`, `name`, `status`) VALUES (#{email}, #{pwdHash}, #{name}, #{status})")
    @Options(useGeneratedKeys = true)
    void insert(Student po);

    @Select("SELECT * FROM `students` WHERE `email` = #{email}")
    Student getByEmail(String email);

    @Select("SELECT * FROM `students` WHERE `id` = #{studentId}")
    Student getById(Integer studentId);

    @Select("UPDATE `students` SET `status` = #{arg1} WHERE `id` = #{arg0}")
    Student updateStatus(Integer studentId, Student.Status status);
}
