export type ScoreMap = Record<string, number>

export interface StudentResponse {
  id: number
  name: string
  age: number
  active: boolean
  subjects: string[]
  scores: ScoreMap
  level: string
  totalScore: number
}

export interface CreateStudentRequest {
  name: string
  age: number
  active: boolean
  subjects: string[]
  scores: ScoreMap
}

export type CourseLevel = 'BEGINNER' | 'INTERMEDIATE' | 'ADVANCED'

export interface CourseResponse {
  id: string
  title: string
  level: CourseLevel
  durationInHours: number
  published: boolean
}

export interface CreateCourseRequest {
  title: string
  level: CourseLevel
  durationInHours: number
  published: boolean
}

export interface LearningInfoResponse {
  title: string
  focusTopics: string[]
}

export interface ApiErrorResponse {
  message: string
  hint: string
}
