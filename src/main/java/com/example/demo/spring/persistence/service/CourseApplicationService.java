package com.example.demo.spring.persistence.service;

import com.example.demo.spring.persistence.domain.Course;
import com.example.demo.spring.persistence.store.CourseStore;
import com.example.demo.spring.springcommon.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// Application service: orchestrates use cases while staying independent from MongoDB details.
public class CourseApplicationService implements CourseService {

	private final CourseStore courseStore;

	public CourseApplicationService(CourseStore courseStore) {
		this.courseStore = courseStore;
	}

	@Override
	public List<Course> findAll() {
		return courseStore.findAll();
	}

	@Override
	public Course findById(String id) {
		return courseStore.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " was not found"));
	}

	@Override
	public Course create(UpsertCourseCommand command) {
		return courseStore.save(
			new Course(
				null,
				command.title(),
				command.level(),
				command.durationInHours(),
				command.published()
			)
		);
	}

	@Override
	public Course update(String id, UpsertCourseCommand command) {
		findById(id);

		return courseStore.save(
			new Course(
				id,
				command.title(),
				command.level(),
				command.durationInHours(),
				command.published()
			)
		);
	}

	@Override
	public void delete(String id) {
		findById(id);
		courseStore.deleteById(id);
	}
}
