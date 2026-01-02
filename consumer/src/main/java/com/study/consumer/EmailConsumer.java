package com.study.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailConsumer {
	private final ObjectMapper objectMapper;

	@KafkaListener(
		topics = "email.send",
		groupId = "email-send-group"
	)
	public void consume(String message) {
		System.out.println("수신 메시지: " + message);

		EmailMessage emailMessage = deserialize(message);

		// TODO: 실제 이메일 발송 로직 구현

		System.out.println("이메일 발송 완료");
	}

	// JSON 역직렬화
	private EmailMessage deserialize(String json) {
		try {
			return objectMapper.readValue(json, EmailMessage.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 역직렬화 실패");
		}
	}
}
