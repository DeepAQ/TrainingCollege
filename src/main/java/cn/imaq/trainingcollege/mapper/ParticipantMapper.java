package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Participant;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ParticipantMapper {
    @Insert("INSERT INTO `participants` (`student_id`, `course_id`, `class_id`, `order_id`, `name`,`status`) VALUES (#{studentId}, #{courseId}, #{classId}, #{orderId}, #{name}, #{status})")
    @Options(useGeneratedKeys = true)
    void insert(Participant po);

    @Select("SELECT count(1) FROM `participants` WHERE `class_id` = #{classId} AND `status` = 1")
    Integer countByClassId(Integer classId);

    @Update("UPDATE `participants` SET `status` = 1 WHERE `order_id` = #{orderId}")
    Integer makeValid(Integer orderId);
}
