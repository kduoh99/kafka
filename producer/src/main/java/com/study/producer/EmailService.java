package com.study.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public void sendEmail(EmailSendReqDto request) {
		// 메시지 객체 생성
		EmailMessage emailMessage = new EmailMessage(
			request.from(),
			request.to(),
			request.subject(),
			request.body()
		);

		// email.send 토픽으로 메시지 전송
		this.kafkaTemplate.send("email.send", serialize(emailMessage));
	}

	// JSON 직렬화
	private String serialize(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 직렬화 실패");
		}
	}
}
