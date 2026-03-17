package com.example.demo.basics.interfaces;

public interface Notifier {

	String channel();

	String send(String message);
}
