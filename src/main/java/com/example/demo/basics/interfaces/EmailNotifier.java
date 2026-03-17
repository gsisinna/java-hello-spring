package com.example.demo.basics.interfaces;

public class EmailNotifier implements Notifier {

	@Override
	public String channel() {
		return "email";
	}

	@Override
	public String send(String message) {
		return "[" + channel() + "] " + message;
	}
}
