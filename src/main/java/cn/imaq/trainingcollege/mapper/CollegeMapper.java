package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.College;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CollegeMapper {
    @Insert("INSERT INTO `colleges` (`pwd_hash`, `profile_id`, `pending_profile_id`) VALUES (#{pwdHash}, #{profileId}, #{pendingProfileId})")
    @Options(useGeneratedKeys = true)
    void insert(College po);

    @Select("SELECT * FROM `colleges` WHERE `id` = #{id}")
    College getById(Integer id);

    @Update("UPDATE `colleges` SET `pending_profile_id` = #{arg1} WHERE `id` = #{arg0}")
    void updatePendingProfile(Integer id, Integer pendingProfileId);
}
