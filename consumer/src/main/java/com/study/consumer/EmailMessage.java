package com.study.consumer;

public record EmailMessage(
	String from,
	String to,
	String subject,
	String body
) {
}
