package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.entity.Student;
import cn.imaq.trainingcollege.mapper.StudentMapper;
import cn.imaq.trainingcollege.support.exception.ServiceException;

@Component
public class PayService {
    @Autumnwired
    private StudentMapper studentMapper;

    public void pay(Integer studentId, Integer amount) {
        while (true) {
            Student student = studentMapper.getById(studentId);
            if (student.getBalance() < amount) {
                throw new ServiceException("余额不足，支付失败");
            }
            if (studentMapper.casBalance(studentId, student.getBalance(), student.getBalance() - amount) > 0) {
                break;
            }
        }
        while (true) {
            Student student = studentMapper.getById(studentId);
            if (studentMapper.casConsumption(studentId, student.getConsumption(), student.getConsumption() + amount) > 0) {
                break;
            }
        }
    }

    public void refund(Integer studentId, Integer amount) {
        while (true) {
            Student student = studentMapper.getById(studentId);
            if (studentMapper.casBalance(studentId, student.getBalance(), student.getBalance() + amount) > 0) {
                break;
            }
        }
        while (true) {
            Student student = studentMapper.getById(studentId);
            if (studentMapper.casConsumption(studentId, student.getConsumption(), student.getConsumption() - amount) > 0) {
                break;
            }
        }
    }
}
