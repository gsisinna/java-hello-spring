package com.example.demo.basics.streams;

import com.example.demo.basics.model.Student;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentAnalyticsTest {

	@Test
	// The assertions match the filter/map/collect pipeline in StudentAnalytics.
	void streamOperationsFilterMapAndCollect() {
		Student ada = new Student("Ada", 20);
		ada.activate();
		ada.addScore("java", 90);

		Student sam = new Student("Sam", 16);
		sam.addScore("java", 70);

		StudentAnalytics analytics = new StudentAnalytics();

		assertEquals(List.of("Ada"), analytics.activeStudentNames(List.of(ada, sam)));
		assertEquals(Map.of("Ada", 90, "Sam", 70), analytics.totalScoresByName(List.of(ada, sam)));
	}
}
