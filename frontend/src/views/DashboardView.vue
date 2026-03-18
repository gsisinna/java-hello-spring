<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useCoursesStore } from '@/stores/courses'
import { useLearningStore } from '@/stores/learning'
import { useSessionStore } from '@/stores/session'
import { useStudentsStore } from '@/stores/students'

const studentsStore = useStudentsStore()
const coursesStore = useCoursesStore()
const learningStore = useLearningStore()
const sessionStore = useSessionStore()

onMounted(async () => {
  if (!studentsStore.students.length) {
    await studentsStore.loadStudents()
  }

  if (!learningStore.learningInfo) {
    await learningStore.loadLearningInfo()
  }

  if (!coursesStore.courses.length && sessionStore.hasCredentials) {
    try {
      await coursesStore.loadCourses(sessionStore.username, sessionStore.password)
    } catch {
      // The dedicated Courses screen surfaces auth errors more explicitly.
    }
  }
})

const connectionState = computed(() => {
  if (studentsStore.error || learningStore.error) {
    return 'Backend issue'
  }

  if (coursesStore.error) {
    return 'Courses locked'
  }

  return 'Connected'
})
</script>

<template>
  <div class="page-shell">
    <v-card class="glass-panel" rounded="xl">
      <v-card-text class="d-grid hero-grid">
        <div>
          <div class="section-kicker">Interactive learning workspace</div>
          <h1 class="section-title">
            Explore Java objects, Spring services, and Mongo-backed APIs through one frontend.
          </h1>
          <p class="section-copy mt-4">
            This dashboard sits on top of the Spring Boot repo and makes the backend easier to test,
            understand, and modify. Use it to inspect students, manage secured courses, and keep the
            learning topics visible while you change the code.
          </p>

          <div class="d-flex flex-wrap ga-3 mt-6">
            <v-chip class="soft-chip" prepend-icon="mdi-connection">
              {{ connectionState }}
            </v-chip>
            <v-chip class="soft-chip" prepend-icon="mdi-school-outline">
              {{ learningStore.learningInfo?.title ?? 'Learning repo' }}
            </v-chip>
            <v-chip class="soft-chip" prepend-icon="mdi-lock-outline">
              Courses use Basic Auth
            </v-chip>
          </div>
        </div>

        <div class="hero-panel glass-panel pa-5">
          <div class="hero-panel-label">Quick launch</div>
          <div class="hero-panel-copy">
            Start from a screen that matches what you want to change in the backend.
          </div>

          <div class="d-grid ga-3 mt-4">
            <v-btn color="primary" size="x-large" to="/students" prepend-icon="mdi-account-group-outline">
              Work on student endpoints
            </v-btn>
            <v-btn color="secondary" size="x-large" to="/courses" prepend-icon="mdi-database-outline">
              Work on course persistence
            </v-btn>
            <v-btn variant="outlined" size="x-large" to="/learn" prepend-icon="mdi-book-open-page-variant-outline">
              Review architecture and concepts
            </v-btn>
          </div>
        </div>
      </v-card-text>
    </v-card>

    <div class="metric-grid">
      <v-card class="glass-panel metric-card">
        <div class="metric-label">Students loaded</div>
        <div class="metric-value">{{ studentsStore.students.length }}</div>
        <div class="metric-note">Public JSON endpoint from `/api/students`</div>
      </v-card>

      <v-card class="glass-panel metric-card">
        <div class="metric-label">Active students</div>
        <div class="metric-value">{{ studentsStore.activeStudents }}</div>
        <div class="metric-note">Good for testing object state and derived values</div>
      </v-card>

      <v-card class="glass-panel metric-card">
        <div class="metric-label">Course hours tracked</div>
        <div class="metric-value">{{ coursesStore.totalHours }}</div>
        <div class="metric-note">Secured Mongo-backed content from `/api/courses`</div>
      </v-card>
    </div>

    <div class="content-grid">
      <v-card class="glass-panel" rounded="xl">
        <v-card-title>Focus topics from the backend config</v-card-title>
        <v-card-subtitle>
          Loaded from the typed configuration properties endpoint.
        </v-card-subtitle>
        <v-card-text>
          <v-alert
            v-if="learningStore.error"
            type="warning"
            variant="tonal"
            density="comfortable"
          >
            {{ learningStore.error }}
          </v-alert>

          <div v-else class="d-flex flex-wrap ga-2">
            <v-chip
              v-for="topic in learningStore.focusTopics"
              :key="topic"
              class="soft-chip"
              size="large"
            >
              {{ topic }}
            </v-chip>
          </div>
        </v-card-text>
      </v-card>

      <v-card class="glass-panel" rounded="xl">
        <v-card-title>Suggested flow</v-card-title>
        <v-card-text class="d-grid ga-3">
          <div class="step-row">
            <div class="step-number">1</div>
            <div>Read the backend model or service you want to change.</div>
          </div>
          <div class="step-row">
            <div class="step-number">2</div>
            <div>Use this frontend to trigger the endpoint and inspect the result quickly.</div>
          </div>
          <div class="step-row">
            <div class="step-number">3</div>
            <div>Run backend tests after each change to keep the learning loop tight.</div>
          </div>
          <div class="step-row">
            <div class="step-number">4</div>
            <div>Open Swagger UI when you need to compare UI behavior with the raw API contract.</div>
          </div>
        </v-card-text>
      </v-card>
    </div>
  </div>
</template>

<style scoped>
.hero-grid {
  gap: 1.25rem;
  grid-template-columns: repeat(12, minmax(0, 1fr));
  padding: 1.25rem;
}

.hero-grid > * {
  grid-column: span 12;
}

.hero-panel {
  background: linear-gradient(160deg, rgba(255, 255, 255, 0.5), rgba(230, 244, 242, 0.72));
}

.hero-panel-label {
  color: rgba(25, 34, 34, 0.62);
  font-size: 0.8rem;
  font-weight: 700;
  letter-spacing: 0.13em;
  text-transform: uppercase;
}

.hero-panel-copy {
  color: rgba(25, 34, 34, 0.72);
  margin-top: 0.6rem;
}

.content-grid {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(12, minmax(0, 1fr));
}

.content-grid > * {
  grid-column: span 12;
}

.step-row {
  align-items: center;
  display: flex;
  gap: 0.9rem;
}

.step-number {
  align-items: center;
  background: rgba(33, 74, 76, 0.1);
  border-radius: 999px;
  color: #214a4c;
  display: grid;
  font-weight: 700;
  height: 2rem;
  min-width: 2rem;
  place-items: center;
}

@media (min-width: 980px) {
  .hero-grid > :first-child {
    grid-column: span 8;
  }

  .hero-grid > :last-child {
    grid-column: span 4;
  }

  .content-grid > * {
    grid-column: span 6;
  }
}
</style>
