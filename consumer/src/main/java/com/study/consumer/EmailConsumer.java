package com.study.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
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
	@RetryableTopic(
		// 총 시도 횟수, 재시도 간격, DLT 접미사
		attempts = "5",
		backoff = @Backoff(delay = 1000, multiplier = 2),
		dltTopicSuffix = ".dlt"
	)
	public void consume(String message) {
		System.out.println("수신 메시지: " + message);

		EmailMessage emailMessage = deserialize(message);

		// 이메일 주소 유효성 검증
		if (emailMessage.to().equals("fail@gmail.com")) {
			System.out.println("유효하지 않은 이메일 주소");
			throw new RuntimeException("이메일 발송 실패");
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException("이메일 발송 실패");
		}

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
