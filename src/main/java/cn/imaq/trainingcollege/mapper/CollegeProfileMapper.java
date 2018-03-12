package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.CollegeProfile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CollegeProfileMapper {
    @Insert("INSERT INTO `college_profiles` (`name`, `location`, `description`) VALUES (#{name}, #{location}, #{description})")
    @Options(useGeneratedKeys = true)
    void insert(CollegeProfile po);

    @Select("SELECT * FROM `college_profiles` WHERE `id` = #{id}")
    CollegeProfile getById(Integer id);
}
