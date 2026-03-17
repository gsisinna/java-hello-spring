package com.example.demo.basics.generics;

// Generic class example: the same class can wrap different types safely.
public class Box<T> {

	private final T value;

	public Box(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
