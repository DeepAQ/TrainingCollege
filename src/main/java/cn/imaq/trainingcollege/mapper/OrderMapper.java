package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.CourseClass;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassMapper {
    @Insert("INSERT INTO `classes` (`course_id`, `teacher`, `price`, `limit`) VALUES (#{courseId}, #{teacher}, #{price}, #{limit})")
    @Options(useGeneratedKeys = true)
    void insert(CourseClass po);

    @Select("SELECT * FROM `classes` WHERE `course_id` = #{courseId}")
    List<CourseClass> getByCourseId(Integer courseId);
}