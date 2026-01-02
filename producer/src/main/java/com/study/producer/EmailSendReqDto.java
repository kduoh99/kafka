package com.study.producer;

public record EmailSendReqDto(
	String from,
	String to,
	String subject,
	String body
) {
}
