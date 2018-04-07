package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Settlement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SettlementMapper {
    @Insert("INSERT INTO `settlements` (`college_id`, `time`, `orig_amount`, `real_amount`) VALUES (#{collegeId}, #{time}, #{origAmount}, #{realAmount})")
    @Options(useGeneratedKeys = true)
    void insert(Settlement po);

    @Select("SELECT * FROM `settlements` WHERE `college_id` = #{collegeId}")
    List<Settlement> getByCollegeId(Integer collegeId);

    @Select("SELECT sum(real_amount) FROM `settlements` WHERE `college_id` = #{collegeId}")
    Integer sumRealByCollegeId(Integer collegeId);

    @Select("SELECT sum(orig_amount) FROM `settlements` WHERE `college_id` = #{collegeId}")
    Integer sumOrigByCollegeId(Integer collegeId);
}
