package com.example.demo.basics.generics;

public class Box<T> {

	private final T value;

	public Box(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
