package com.study.producer;

public record EmailMessage(
	String from,
	String to,
	String subject,
	String body
) {
}
