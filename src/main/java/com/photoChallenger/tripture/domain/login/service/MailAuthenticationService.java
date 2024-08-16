package com.photoChallenger.tripture.domain.login.service;

import com.photoChallenger.tripture.global.exception.redis.AlreadyCheckUserException;
import com.photoChallenger.tripture.global.exception.redis.EmailAuthValidTimeOverException;
import com.photoChallenger.tripture.global.redis.RedisDao;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailAuthenticationService {

    private final JavaMailSender javaMailSender;
    private final RedisDao redisDao;
    private static final String senderEmail = "${spring.mail.username}";
    private static int authNum;

    public static void makeRandomNumber() {
        authNum = (int)(Math.random() * (90000)) + 100000; //(int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    public String createMail(String toMail) {
        makeRandomNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        String title = "[Tripture] 회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content =
                "<h2>안녕하세요 Triptrue입니다. 회원가입을 위해 요청하신 인증 번호입니다.</h2>" + 	//html 형식으로 작성 !
                        "<br>" +
                        "<h1>" + authNum + "</h1>" +
                        "<br>" +
                        "<h3>회원가입 창으로 돌아가 인증 번호를 입력해 주세요.</h3>" +
                        "<h3>감사합니다.</h3>"; //이메일 내용 삽입

        String redisKey = "user:email:" + toMail; // 조회수 key

        redisDao.setValues(redisKey, Integer.toString(authNum));
        redisDao.expireValues(redisKey, 10);

        sendMail(senderEmail, toMail, title, content);

        return Integer.toString(authNum);
    }

    public void sendMail(String setFrom, String toMail, String title, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.info(e.getMessage());
        }
    }

    public boolean CheckAuthNum(String email, String authNum) {
        String redisKey = "user:email:" + email;

        if(redisDao.checkExistsValue(redisKey)) {
            throw new EmailAuthValidTimeOverException();
        }

        if(redisDao.getValues(redisKey).equals(authNum)) {
            return true;
        } else {
            return false;
        }
    }
}
