package com.example.demo.spring.persistence.repository;

import com.example.demo.spring.persistence.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data creates the implementation automatically at runtime.
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
}
