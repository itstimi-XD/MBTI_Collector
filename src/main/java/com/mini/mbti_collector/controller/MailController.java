package com.mini.mbti_collector.controller;

import com.mini.mbti_collector.dto.MailDto;
import com.mini.mbti_collector.service.MailSendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailSendService mailSendService;

    private static final Logger log = LoggerFactory.getLogger(MailController.class);


    @PostMapping(value = "/api/email-verification", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailDto.Response> mailCheck(@Valid @RequestBody MailDto.Request requestDto) { // @Valid를 추가하여 검증을 실행합니다. 만약 검증에 실패하면, Spring은 MethodArgumentNotValidException를 발생시킵니다.

        String authNum = mailSendService.joinEmail(requestDto.getEmail());
        MailDto.Response response = new MailDto.Response();
        response.setAuthNum(authNum);
        response.setMessage("인증번호가 발송되었습니다.");
        response.setStatus(HttpStatus.OK.value());

        log.info("email : " + requestDto.getEmail());
        log.info("checkNum : " + authNum);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
