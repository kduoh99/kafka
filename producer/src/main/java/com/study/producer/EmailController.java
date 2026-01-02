package com.study.producer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emails")
public class EmailController {
	private final EmailService emailService;

	@PostMapping
	public ResponseEntity<String> sendEmail(@RequestBody EmailSendReqDto request) {
		emailService.sendEmail(request);
		return ResponseEntity.ok("이메일 발송 요청");
	}
}

