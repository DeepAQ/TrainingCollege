package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Insert("INSERT INTO `courses` (`college_id`, `title`, `description`, `tags`, `start_time`, `period`, `weeks`) VALUES (#{collegeId}, #{title}, #{description}, #{tags}, #{startTime}, #{period}, #{weeks})")
    @Options(useGeneratedKeys = true)
    void insert(Course po);

    @Select("SELECT * FROM `courses` WHERE `id` = #{id}")
    Course getById(Integer id);

    @Select("SELECT * FROM `courses` WHERE `college_id` = #{collegeId} ORDER BY `start_time` DESC")
    List<Course> getByCollegeId(Integer collegeId);
}
