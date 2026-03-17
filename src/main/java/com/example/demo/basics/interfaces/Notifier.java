package com.example.demo.basics.interfaces;

// Interface example: callers depend on a contract, not a concrete implementation.
public interface Notifier {

	String channel();

	String send(String message);
}
