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
        casConsumption(studentId, amount);
        casPoints(studentId, amount / 100);
    }

    public void refund(Integer studentId, Integer amount) {
        while (true) {
            Student student = studentMapper.getById(studentId);
            if (studentMapper.casBalance(studentId, student.getBalance(), student.getBalance() + amount) > 0) {
                break;
            }
        }
        casConsumption(studentId, -amount);
        casPoints(studentId, -amount / 100);
    }

    public void exchangePoints(Integer studentId, Integer points) {
        while (true) {
            Student student = studentMapper.getById(studentId);
            if (student.getPoints() < points) {
                throw new ServiceException("积分不足，兑换失败");
            }
            if (studentMapper.casPoints(studentId, student.getPoints(), student.getPoints() - points) > 0) {
                break;
            }
        }
        while (true) {
            Student student = studentMapper.getById(studentId);
            if (studentMapper.casBalance(studentId, student.getBalance(), student.getBalance() + points) > 0) {
                break;
            }
        }
    }

    public void casConsumption(Integer studentId, Integer delta) {
        while (true) {
            Student student = studentMapper.getById(studentId);
            if (studentMapper.casConsumption(studentId, student.getConsumption(), student.getConsumption() + delta) > 0) {
                break;
            }
        }
    }

    public void casPoints(Integer studentId, Integer delta) {
        while (true) {
            Student student = studentMapper.getById(studentId);
            if (studentMapper.casPoints(studentId, student.getPoints(), student.getPoints() + delta) > 0) {
                break;
            }
        }
    }
}
