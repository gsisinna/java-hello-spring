import { http, createBasicAuthHeader } from './http'
import type {
  CourseResponse,
  CreateCourseRequest,
  CreateStudentRequest,
  LearningInfoResponse,
  StudentResponse,
} from '@/types/api'

export async function fetchStudents() {
  const { data } = await http.get<StudentResponse[]>('/api/students')
  return data
}

export async function createStudent(payload: CreateStudentRequest) {
  const { data } = await http.post<StudentResponse>('/api/students', payload)
  return data
}

export async function fetchCourses(username: string, password: string) {
  const { data } = await http.get<CourseResponse[]>('/api/courses', {
    headers: {
      Authorization: createBasicAuthHeader(username, password),
    },
  })
  return data
}

export async function createCourse(payload: CreateCourseRequest, username: string, password: string) {
  const { data } = await http.post<CourseResponse>('/api/courses', payload, {
    headers: {
      Authorization: createBasicAuthHeader(username, password),
    },
  })
  return data
}

export async function updateCourse(
  id: string,
  payload: CreateCourseRequest,
  username: string,
  password: string,
) {
  const { data } = await http.put<CourseResponse>(`/api/courses/${id}`, payload, {
    headers: {
      Authorization: createBasicAuthHeader(username, password),
    },
  })
  return data
}

export async function deleteCourse(id: string, username: string, password: string) {
  await http.delete(`/api/courses/${id}`, {
    headers: {
      Authorization: createBasicAuthHeader(username, password),
    },
  })
}

export async function fetchLearningInfo() {
  const { data } = await http.get<LearningInfoResponse>('/api/learning-info')
  return data
}
