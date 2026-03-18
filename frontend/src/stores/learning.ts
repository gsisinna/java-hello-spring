import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { fetchLearningInfo } from '@/services/learning-api'
import type { LearningInfoResponse } from '@/types/api'
import axios from 'axios'

export const useLearningStore = defineStore('learning', () => {
  const learningInfo = ref<LearningInfoResponse | null>(null)
  const loading = ref(false)
  const error = ref<string | null>(null)

  const focusTopics = computed(() => learningInfo.value?.focusTopics ?? [])

  async function loadLearningInfo() {
    loading.value = true
    error.value = null

    try {
      learningInfo.value = await fetchLearningInfo()
    } catch (loadError) {
      error.value = toMessage(loadError, 'Could not load the learning info')
    } finally {
      loading.value = false
    }
  }

  return {
    learningInfo,
    loading,
    error,
    focusTopics,
    loadLearningInfo,
  }
})

function toMessage(error: unknown, fallback: string) {
  if (axios.isAxiosError(error)) {
    return error.response?.data?.message ?? error.message
  }
  return fallback
}
