package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO `orders` (`student_id`, `college_id`, `course_id`, `class_id`, `count`, `orig_price`, `pay_price`, `status`, `created`) VALUES (#{studentId}, #{collegeId}, #{courseId}, #{classId}, #{count}, #{orig_price}, #{pay_price}, #{status}, #{created})")
    @Options(useGeneratedKeys = true)
    void insert(Order po);

    @Select("SELECT * FROM `orders` WHERE `student_id` = #{studentId}")
    List<Order> getByStudentId(Integer studentId);

    @Update("UPDATE `orders` SET `status` = #{arg1} WHERE `id` = #{arg0}")
    void updateStatus(Integer id, Order.Status status);
}
