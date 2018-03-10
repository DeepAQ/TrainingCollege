package cn.imaq.trainingcollege.service;

import cn.imaq.autumn.core.annotation.Autumnwired;
import cn.imaq.autumn.core.annotation.Component;
import cn.imaq.trainingcollege.domain.dto.StudentRegisterDto;
import cn.imaq.trainingcollege.domain.entity.Student;
import cn.imaq.trainingcollege.mapper.StudentMapper;
import cn.imaq.trainingcollege.support.exception.ServiceException;
import cn.imaq.trainingcollege.util.HashUtil;
import cn.imaq.trainingcollege.util.JWTUtil;
import cn.imaq.trainingcollege.util.MailUtil;
import cn.imaq.trainingcollege.util.Sensitive;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentService {
    @Autumnwired
    private StudentMapper studentMapper;

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
        try {
            sendActivicationEmail(dto.getEmail());
        } catch (MessagingException ignored) {
        }
    }

    public void sendActivicationEmail(String email) throws MessagingException {
        Student student = studentMapper.getByEmail(email);
        if (student == null) {
            throw new ServiceException("该邮箱未注册");
        }
        if (student.getStatus() != Student.Status.NOT_VERIFIED) {
            throw new ServiceException("用户已激活，无需重复发送");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        String token = JWTUtil.sign(claims);
        String link = Sensitive.BASE_URL + "/activate.html?token=" + token;
        MailUtil.sendAsync(MailUtil.subject("TrainingCollege 学员账号激活")
                .from("TrainingCollege")
                .to(email)
                .html("<div>欢迎注册 TrainingCollege，请点击以下链接激活账号：<br><a href=\"" + link + "\">" + link + "</a></div>")
        );
    }
}
