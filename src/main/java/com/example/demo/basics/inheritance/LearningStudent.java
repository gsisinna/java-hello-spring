package com.example.demo.basics.inheritance;

// Child class that inherits state from Person and overrides behavior.
public class LearningStudent extends Person {

	private final String track;

	public LearningStudent(String name, String track) {
		super(name);
		this.track = track;
	}

	@Override
	public String description() {
		return "Student: " + getName() + " studies " + track;
	}

	public String getTrack() {
		return track;
	}
}
