package cn.imaq.trainingcollege.mapper;

import cn.imaq.trainingcollege.domain.entity.Grade;
import cn.imaq.trainingcollege.domain.entity.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper {
    @Insert("INSERT INTO `records` (`participant_id`, `date`, `note`) VALUES (#{participantId}, #{date}, #{note})")
    @Options(useGeneratedKeys = true)
    void insertRecord(Record po);

    @Insert("INSERT INTO `grades` (`participant_id`, `date`, `grade`, `note`) VALUES (#{participantId}, #{date}, #{grade}, #{note})")
    @Options(useGeneratedKeys = true)
    void insertGrade(Grade po);

    @Select("SELECT * FROM `records` WHERE `participant_id` = #{participantId} ORDER BY `date` ASC")
    List<Record> getRecordsByParticipantId(Integer participantId);

    @Select("SELECT * FROM `grades` WHERE `participant_id` = #{participantId} ORDER BY `date` ASC")
    List<Grade> getGradesByParticipantId(Integer participantId);
}
