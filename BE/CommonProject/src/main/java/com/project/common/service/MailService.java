package com.project.common.service;

import com.project.common.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;

    private static final String title = "좋구만유 project 회원가입 이메일 인증 안내 메일입니다.";
    private static final String message = "안녕하세요. 좋구만유 project 회원가입 이메일 인증 안내 메일입니다. "
            +"\n" + "회원님의 인증번호는 아래와 같습니다. 인증번호를 회원가입 창 빈칸에 올바르게 적어주십시오."+"\n";
    private static final String fromAddress = "withcultureproject@gmail.com";

    /** 이메일 생성 **/
    public MailDto createMail(String tmpPassword, String memberEmail) {

        MailDto mailDto = MailDto.builder()
                .toAddress(memberEmail)
                .title(title)
                .message(message + tmpPassword)
                .fromAddress(fromAddress)
                .build();

        return mailDto;
    }

    /** 이메일 전송 **/
    public void sendMail(MailDto mailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDto.getToAddress());
        mailMessage.setSubject(mailDto.getTitle());
        mailMessage.setText(mailDto.getMessage());
        mailMessage.setFrom(mailDto.getFromAddress());
        mailMessage.setReplyTo(mailDto.getFromAddress());

        mailSender.send(mailMessage);
    }
}
