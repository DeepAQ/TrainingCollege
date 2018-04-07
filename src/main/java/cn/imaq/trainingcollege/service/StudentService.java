package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.config.Sensitive;
import cn.imaq.trainingcollege.domain.dto.*;
import cn.imaq.trainingcollege.domain.entity.Student;
import cn.imaq.trainingcollege.domain.enumeration.UserType;
import cn.imaq.trainingcollege.mapper.StudentMapper;
import cn.imaq.trainingcollege.support.exception.ServiceException;
import cn.imaq.trainingcollege.util.HashUtil;
import cn.imaq.trainingcollege.util.JWTUtil;
import cn.imaq.trainingcollege.util.MailUtil;

@Component
public class StudentService {
    @Autumnwired
    private StudentMapper studentMapper;

    @Autumnwired
    private PayService payService;

    public LoginResultDto login(StudentLoginDto dto) {
        Student student = studentMapper.getByEmail(dto.getEmail());
        if (student == null) {
            throw new ServiceException("该邮箱未被注册");
        }
        if (!student.getPwdHash().equals(HashUtil.hash(dto.getPassword()))) {
            throw new ServiceException("密码错误");
        }
        LoginClaim claim = new LoginClaim(student.getId(), UserType.Student);
        String token = JWTUtil.sign(claim);
        return new LoginResultDto(token, student.getName(), UserType.Student, student.getStatus() == Student.Status.NOT_VERIFIED);
    }

    public void register(StudentRegisterDto dto) {
        Student student = studentMapper.getByEmail(dto.getEmail());
        if (student != null) {
            throw new ServiceException("该邮箱已被注册");
        }
        student = Student.builder()
                .email(dto.getEmail())
                .pwdHash(HashUtil.hash(dto.getPassword()))
                .name(dto.getName())
                .status(Student.Status.NOT_VERIFIED)
                .build();
        studentMapper.insert(student);
        sendActivicationEmail(student.getId());
    }

    public void sendActivicationEmail(Integer studentId) {
        Student student = studentMapper.getById(studentId);
        if (student == null) {
            throw new ServiceException("用户不存在");
        }
        if (student.getStatus() != Student.Status.NOT_VERIFIED) {
            throw new ServiceException("用户已激活，无需重复发送");
        }
        String token = JWTUtil.sign(student.getEmail());
        String link = Sensitive.BASE_URL + "/#/activate/" + token;
        try {
            MailUtil.sendAsync(MailUtil.subject("TrainingCollege 学员账号激活")
                    .from("TrainingCollege")
                    .to(student.getEmail())
                    .html("<div>欢迎注册 TrainingCollege，请点击以下链接激活账号：<br><a href=\"" + link + "\">" + link + "</a></div>")
            );
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public StudentProfileDto getProfile(Integer id) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new ServiceException("用户不存在");
        }
        return StudentProfileDto.builder()
                .email(student.getEmail())
                .name(student.getName())
                .status(student.getStatus())
                .build();
    }

    public void terminate(Integer id) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new ServiceException("用户不存在");
        }
        if (student.getStatus() != Student.Status.VERIFIED) {
            throw new ServiceException("该状态下无法注销");
        }
        studentMapper.updateStatus(id, Student.Status.TERMINATED);
    }

    public StudentWalletDto getWallet(Integer id) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new ServiceException("用户不存在");
        }
        return StudentWalletDto.builder()
                .balance(student.getBalance())
                .points(student.getPoints())
                .consumption(student.getConsumption())
                .discount(discountLevel(student))
                .build();
    }

    public void exchangePoints(Integer id, Integer points) {
        if (points <= 0) {
            throw new ServiceException("FA♂Q");
        }
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new ServiceException("用户不存在");
        }
        payService.exchangePoints(id, points);
    }

    public Integer getDiscountLevel(Integer id) {
        Student student = studentMapper.getById(id);
        if (student == null) {
            throw new ServiceException("用户不存在");
        }
        return discountLevel(student);
    }

    private int discountLevel(Student student) {
        int discount = student.getConsumption() / 10000 * 5;
        if (discount > 50) {
            discount = 50;
        }
        return discount;
    }
}
