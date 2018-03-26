package cn.imaq.trainingcollege.util;

import cn.imaq.trainingcollege.config.Sensitive;
import io.github.biezhi.ome.OhMyEmail;

import javax.mail.MessagingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailUtil {
    private static ExecutorService executor = Executors.newCachedThreadPool();

    static {
        OhMyEmail.config(OhMyEmail.SMTP_ENT_QQ(false), Sensitive.MAIL_USERNAME, Sensitive.MAIL_PASSWORD);
    }

    public static OhMyEmail subject(String subject) throws MessagingException {
        return OhMyEmail.subject(subject);
    }

    public static void sendAsync(OhMyEmail ohMyEmail) {
        executor.submit(() -> {
            try {
                ohMyEmail.send();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
}
