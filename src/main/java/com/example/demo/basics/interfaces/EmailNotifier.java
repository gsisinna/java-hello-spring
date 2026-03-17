package com.example.demo.basics.interfaces;

// One implementation of the Notifier contract.
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
