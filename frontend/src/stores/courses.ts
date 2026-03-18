import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { createCourse, deleteCourse, fetchCourses, updateCourse } from '@/services/learning-api'
import type { CourseResponse, CreateCourseRequest } from '@/types/api'
import axios from 'axios'

export const useCoursesStore = defineStore('courses', () => {
  const courses = ref<CourseResponse[]>([])
  const loading = ref(false)
  const saving = ref(false)
  const error = ref<string | null>(null)
  const lastLoadedAt = ref<string | null>(null)

  const publishedCourses = computed(() => courses.value.filter((course) => course.published).length)
  const totalHours = computed(() =>
    courses.value.reduce((total, course) => total + course.durationInHours, 0),
  )

  async function loadCourses(username: string, password: string) {
    loading.value = true
    error.value = null

    try {
      courses.value = await fetchCourses(username, password)
      lastLoadedAt.value = new Date().toLocaleTimeString()
    } catch (loadError) {
      error.value = toMessage(loadError, 'Could not load the course catalog')
      throw loadError
    } finally {
      loading.value = false
    }
  }

  async function addCourse(payload: CreateCourseRequest, username: string, password: string) {
    saving.value = true
    error.value = null

    try {
      const created = await createCourse(payload, username, password)
      courses.value = [created, ...courses.value]
      return created
    } catch (saveError) {
      error.value = toMessage(saveError, 'Could not create the course')
      throw saveError
    } finally {
      saving.value = false
    }
  }

  async function saveCourse(
    id: string,
    payload: CreateCourseRequest,
    username: string,
    password: string,
  ) {
    saving.value = true
    error.value = null

    try {
      const updated = await updateCourse(id, payload, username, password)
      courses.value = courses.value.map((course) => (course.id === updated.id ? updated : course))
      return updated
    } catch (saveError) {
      error.value = toMessage(saveError, 'Could not update the course')
      throw saveError
    } finally {
      saving.value = false
    }
  }

  async function removeCourse(id: string, username: string, password: string) {
    saving.value = true
    error.value = null

    try {
      await deleteCourse(id, username, password)
      courses.value = courses.value.filter((course) => course.id !== id)
    } catch (saveError) {
      error.value = toMessage(saveError, 'Could not delete the course')
      throw saveError
    } finally {
      saving.value = false
    }
  }

  return {
    courses,
    loading,
    saving,
    error,
    lastLoadedAt,
    publishedCourses,
    totalHours,
    loadCourses,
    addCourse,
    saveCourse,
    removeCourse,
  }
})

function toMessage(error: unknown, fallback: string) {
  if (axios.isAxiosError(error)) {
    return error.response?.data?.message ?? error.message
  }
  return fallback
}
