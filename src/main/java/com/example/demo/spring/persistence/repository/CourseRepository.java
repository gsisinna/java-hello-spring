package com.example.demo.spring.persistence.repository;

import com.example.demo.spring.persistence.document.CourseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

// Spring Data creates the implementation automatically at runtime for MongoDB.
public interface CourseRepository extends MongoRepository<CourseDocument, String> {
}
