import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { createStudent, fetchStudents } from '@/services/learning-api'
import type { CreateStudentRequest, StudentResponse } from '@/types/api'
import axios from 'axios'

export const useStudentsStore = defineStore('students', () => {
  const students = ref<StudentResponse[]>([])
  const loading = ref(false)
  const saving = ref(false)
  const error = ref<string | null>(null)

  const activeStudents = computed(() => students.value.filter((student) => student.active).length)
  const averageTotalScore = computed(() => {
    if (!students.value.length) {
      return 0
    }

    const sum = students.value.reduce((total, student) => total + student.totalScore, 0)
    return Math.round(sum / students.value.length)
  })

  async function loadStudents() {
    loading.value = true
    error.value = null

    try {
      students.value = await fetchStudents()
    } catch (loadError) {
      error.value = toMessage(loadError, 'Could not load students')
    } finally {
      loading.value = false
    }
  }

  async function addStudent(payload: CreateStudentRequest) {
    saving.value = true
    error.value = null

    try {
      const created = await createStudent(payload)
      students.value = [created, ...students.value]
      return created
    } catch (saveError) {
      error.value = toMessage(saveError, 'Could not create the student')
      throw saveError
    } finally {
      saving.value = false
    }
  }

  return {
    students,
    loading,
    saving,
    error,
    activeStudents,
    averageTotalScore,
    loadStudents,
    addStudent,
  }
})

function toMessage(error: unknown, fallback: string) {
  if (axios.isAxiosError(error)) {
    return error.response?.data?.message ?? error.message
  }
  return fallback
}
