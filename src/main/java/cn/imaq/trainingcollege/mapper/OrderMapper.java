package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO `orders` (`student_id`, `college_id`, `course_id`, `class_id`, `count`, `orig_price`, `pay_price`, `status`, `created`) VALUES (#{studentId}, #{collegeId}, #{courseId}, #{classId}, #{count}, #{origPrice}, #{payPrice}, #{status}, #{created})")
    @Options(useGeneratedKeys = true)
    void insert(Order po);

    @Select("SELECT * FROM `orders` WHERE `id` = #{id}")
    Order getById(Integer id);

    @Select("SELECT * FROM `orders` WHERE `created` BETWEEN #{arg0} AND #{arg1} ORDER BY `created` DESC")
    List<Order> getByTime(Integer start, Integer end);

    @Select("SELECT * FROM `orders` WHERE `student_id` = #{studentId} ORDER BY `created` DESC")
    List<Order> getByStudentId(Integer studentId);

    @Select("SELECT * FROM `orders` WHERE `college_id` = #{collegeId} ORDER BY `created` DESC")
    List<Order> getByCollegeId(Integer collegeId);

    @Select("SELECT * FROM `orders` WHERE `college_id` = #{arg0} AND `class_id` = 0 AND `status` = #{arg1} ORDER BY `created` DESC")
    List<Order> getPendingsByCollegeId(Integer collegeId, Order.Status status);

    @Update("UPDATE `orders` SET `status` = #{arg1} WHERE `id` = #{arg0}")
    Integer updateStatus(Integer id, Order.Status status);

    @Update("UPDATE `orders` SET `pay_price` = #{arg1} WHERE `id` = #{arg0}")
    Integer updatePayPrice(Integer id, Integer payPrice);

    @Update("UPDATE `orders` SET `class_id` = #{arg1} WHERE `id` = #{arg0}")
    Integer updateClassId(Integer id, Integer classId);

    @Select("SELECT sum(pay_price) FROM `orders` WHERE `college_id` = #{arg0} AND `status` = #{arg1}")
    Integer sumByCollegeId(Integer collegeId, Order.Status status);

    @Select("SELECT sum(count) FROM `orders` WHERE `course_id` = #{arg0} AND `status` = #{arg1}")
    Integer sumCountByCourseId(Integer course, Order.Status status);

    @Select("SELECT sum(pay_price) FROM `orders` WHERE `course_id` = #{arg0} AND `status` = #{arg1}")
    Integer sumPriceByCourseId(Integer course, Order.Status status);
}
