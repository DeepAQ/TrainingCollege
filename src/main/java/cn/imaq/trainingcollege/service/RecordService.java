package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.entity.Grade;
import cn.imaq.trainingcollege.domain.entity.Record;
import cn.imaq.trainingcollege.mapper.RecordMapper;

import java.util.List;

@Component
public class RecordService {
    @Autumnwired
    private RecordMapper recordMapper;

    public List<Record> getRecords(Integer participantId) {
        return recordMapper.getRecordsByParticipantId(participantId);
    }

    public List<Grade> getGrades(Integer participantId) {
        return recordMapper.getGradesByParticipantId(participantId);
    }

    public void addRecord(Record record) {
        recordMapper.insertRecord(record);
    }

    public void addGrade(Grade grade) {
        recordMapper.insertGrade(grade);
    }
}
