package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentMapper {
    @Insert("INSERT INTO `students` (`email`, `pwd_hash`, `name`, `status`) VALUES (#{email}, #{pwdHash}, #{name}, #{status})")
    @Options(useGeneratedKeys = true)
    void insert(Student po);

    @Select("SELECT * FROM `students` WHERE `email` = #{email}")
    Student getByEmail(String email);

    @Select("SELECT * FROM `students` WHERE `id` = #{studentId}")
    Student getById(Integer studentId);

    @Update("UPDATE `students` SET `status` = #{arg1} WHERE `id` = #{arg0}")
    Integer updateStatus(Integer studentId, Student.Status status);

    @Update("UPDATE `students` SET `pwd_hash` = #{arg1} WHERE `id` = #{arg0}")
    Integer updatePwdHash(Integer studentId, String pwdHash);

    @Update("UPDATE `students` SET `balance` = #{arg2} WHERE `id` = #{arg0} AND `balance` = #{arg1}")
    Integer casBalance(Integer studentId, Integer oldValue, Integer newValue);

    @Update("UPDATE `students` SET `consumption` = #{arg2} WHERE `id` = #{arg0} AND `consumption` = #{arg1}")
    Integer casConsumption(Integer studentId, Integer oldValue, Integer newValue);

    @Update("UPDATE `students` SET `points` = #{arg2} WHERE `id` = #{arg0} AND `points` = #{arg1}")
    Integer casPoints(Integer studentId, Integer oldValue, Integer newValue);
}
