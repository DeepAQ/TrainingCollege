package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.College;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CollegeMapper {
    @Insert("INSERT INTO `colleges` (`pwd_hash`, `profile_id`, `pending_profile_id`) VALUES (#{pwdHash}, #{profileId}, #{pendingProfileId})")
    @Options(useGeneratedKeys = true)
    void insert(College po);

    @Select("SELECT * FROM `colleges` WHERE `id` = #{id}")
    College getById(Integer id);

    @Select("SELECT * FROM `colleges` WHERE `pending_profile_id` IS NOT NULL")
    List<College> getPendings();

    @Select("SELECT * FROM `colleges` WHERE `profile_id` IS NOT NULL")
    List<College> getAll();

    @Update("UPDATE `colleges` SET `profile_id` = #{arg1}, `pending_profile_id` = NULL WHERE `id` = #{arg0}")
    Integer updateProfile(Integer id, Integer profileId);

    @Update("UPDATE `colleges` SET `pending_profile_id` = #{arg1} WHERE `id` = #{arg0}")
    Integer updatePendingProfile(Integer id, Integer pendingProfileId);
}
