<script setup lang="ts">
import { computed, onMounted, reactive } from 'vue'
import { useStudentsStore } from '@/stores/students'
import type { CreateStudentRequest } from '@/types/api'

const studentsStore = useStudentsStore()

const form = reactive({
  name: '',
  age: 21,
  active: true,
  subjectsText: 'classes and objects, methods',
  javaScore: 85,
  springScore: 78,
})

const filters = reactive({
  query: '',
  activeOnly: false,
})

onMounted(async () => {
  if (!studentsStore.students.length) {
    await studentsStore.loadStudents()
  }
})

const topStudent = computed(() =>
  [...studentsStore.students].sort((left, right) => right.totalScore - left.totalScore)[0],
)

const filteredStudents = computed(() => {
  return studentsStore.students.filter((student) => {
    const matchesQuery = [student.name, student.level, ...student.subjects]
      .join(' ')
      .toLowerCase()
      .includes(filters.query.toLowerCase())
    const matchesActive = !filters.activeOnly || student.active
    return matchesQuery && matchesActive
  })
})

const previewPayload = computed(() => JSON.stringify({
  name: form.name || 'Lina',
  age: form.age,
  active: form.active,
  subjects: form.subjectsText
    .split(',')
    .map((subject) => subject.trim())
    .filter(Boolean),
  scores: {
    java: form.javaScore,
    spring: form.springScore,
  },
}, null, 2))

async function submit() {
  const payload: CreateStudentRequest = {
    name: form.name,
    age: form.age,
    active: form.active,
    subjects: form.subjectsText
      .split(',')
      .map((subject) => subject.trim())
      .filter(Boolean),
    scores: {
      java: form.javaScore,
      spring: form.springScore,
    },
  }

  await studentsStore.addStudent(payload)
  reset()
}

function reset() {
  form.name = ''
  form.age = 21
  form.active = true
  form.subjectsText = 'classes and objects, methods'
  form.javaScore = 85
  form.springScore = 78
}
</script>

<template>
  <div class="page-shell">
    <div class="page-header page-banner glass-panel">
      <div>
        <div class="section-kicker">Public student API</div>
        <h1 class="section-title">Test classes, collections, and request models from one screen.</h1>
        <p class="section-copy mt-4">
          The student flow is intentionally simple. It is the easiest place to understand how Spring
          controllers map JSON request bodies into objects and how the service shapes the response.
        </p>
      </div>
      <div class="banner-callout">
        <div class="banner-label">Best use</div>
        <div class="banner-copy">
          Change the Java model or service, reload this page, and verify the JSON result immediately.
        </div>
      </div>
    </div>

    <div class="metric-grid">
      <v-card class="glass-panel metric-card">
        <div class="metric-label">Average total score</div>
        <div class="metric-value">{{ studentsStore.averageTotalScore }}</div>
        <div class="metric-note">Computed from the response DTOs in the frontend</div>
      </v-card>

      <v-card class="glass-panel metric-card">
        <div class="metric-label">Top student</div>
        <div class="metric-value text-wrap">{{ topStudent?.name ?? 'No data' }}</div>
        <div class="metric-note">Useful for checking derived response fields</div>
      </v-card>

      <v-card class="glass-panel metric-card">
        <div class="metric-label">Backend status</div>
        <div class="metric-value">{{ studentsStore.error ? 'Issue' : 'Ready' }}</div>
        <div class="metric-note">{{ studentsStore.error ?? 'Student endpoints are reachable.' }}</div>
      </v-card>
    </div>

    <div class="layout-grid">
      <v-card class="glass-panel" rounded="xl">
        <v-card-title>Create a student</v-card-title>
        <v-card-subtitle>
          This posts to `/api/students` and immediately inserts the created response at the top.
        </v-card-subtitle>
        <v-card-text>
          <v-form class="d-grid ga-4" @submit.prevent="submit">
            <v-text-field
              v-model="form.name"
              label="Name"
              placeholder="Lina"
              required
            />

            <div class="split-grid">
              <v-text-field
                v-model.number="form.age"
                label="Age"
                type="number"
                min="1"
              />
              <v-switch v-model="form.active" color="primary" inset label="Active" />
            </div>

            <v-textarea
              v-model="form.subjectsText"
              label="Subjects"
              hint="Comma-separated topics"
              persistent-hint
              rows="3"
            />

            <div class="split-grid">
              <v-text-field
                v-model.number="form.javaScore"
                label="Java score"
                type="number"
                min="0"
                max="100"
              />
              <v-text-field
                v-model.number="form.springScore"
                label="Spring score"
                type="number"
                min="0"
                max="100"
              />
            </div>

            <v-alert
              v-if="studentsStore.error"
              type="warning"
              variant="tonal"
              density="comfortable"
            >
              {{ studentsStore.error }}
            </v-alert>

            <div class="d-flex flex-wrap ga-3">
              <v-btn
                color="primary"
                :loading="studentsStore.saving"
                prepend-icon="mdi-account-plus-outline"
                :disabled="!form.name.trim()"
                type="submit"
              >
                Create student
              </v-btn>
              <v-btn variant="text" @click="reset">Reset</v-btn>
              <v-btn variant="outlined" prepend-icon="mdi-refresh" @click="studentsStore.loadStudents()">
                Reload list
              </v-btn>
            </div>
          </v-form>

          <div class="code-preview mt-6">
            <div class="code-preview-label">Request preview</div>
            <pre>{{ previewPayload }}</pre>
          </div>
        </v-card-text>
      </v-card>

      <v-card class="glass-panel" rounded="xl">
        <v-card-title>Student responses</v-card-title>
        <v-card-subtitle>
          Useful for understanding how the backend computes `level` and `totalScore`.
        </v-card-subtitle>
        <v-card-text>
          <div class="toolbar-row">
            <v-text-field
              v-model="filters.query"
              label="Search students or topics"
              prepend-inner-icon="mdi-magnify"
              hide-details
            />
            <v-switch
              v-model="filters.activeOnly"
              color="primary"
              inset
              hide-details
              label="Active only"
            />
          </div>

          <v-progress-linear
            v-if="studentsStore.loading"
            indeterminate
            color="primary"
            rounded
          />

          <v-alert
            v-else-if="studentsStore.error"
            type="warning"
            variant="tonal"
            density="comfortable"
            class="mt-4"
          >
            {{ studentsStore.error }}
          </v-alert>

          <div v-else-if="!filteredStudents.length" class="empty-state">
            <v-icon icon="mdi-account-search-outline" size="40" />
            <div class="empty-title">No students match this filter.</div>
            <div class="empty-copy">Clear the search or add a new student from the form.</div>
          </div>

          <div v-else class="d-grid ga-4">
            <v-card
              v-for="student in filteredStudents"
              :key="student.id"
              class="student-card"
              rounded="xl"
              variant="tonal"
            >
              <v-card-text>
                <div class="d-flex justify-space-between flex-wrap ga-2">
                  <div>
                    <div class="student-name">{{ student.name }}</div>
                    <div class="student-meta">
                      age {{ student.age }} · {{ student.level }} · ID {{ student.id }}
                    </div>
                  </div>

                  <v-chip :color="student.active ? 'success' : 'warning'" size="small">
                    {{ student.active ? 'active' : 'inactive' }}
                  </v-chip>
                </div>

                <div class="d-flex flex-wrap ga-2 mt-4">
                  <v-chip
                    v-for="subject in student.subjects"
                    :key="subject"
                    class="soft-chip"
                    size="small"
                  >
                    {{ subject }}
                  </v-chip>
                </div>

                <div class="score-grid mt-4">
                  <div
                    v-for="(score, subject) in student.scores"
                    :key="subject"
                    class="score-pill"
                  >
                    <span>{{ subject }}</span>
                    <strong>{{ score }}</strong>
                  </div>
                </div>

                <div class="student-total mt-4">
                  Total score: <strong>{{ student.totalScore }}</strong>
                </div>
              </v-card-text>
            </v-card>
          </div>
        </v-card-text>
      </v-card>
    </div>
  </div>
</template>

<style scoped>
.page-header {
  padding-bottom: 0.25rem;
}

.page-banner {
  align-items: start;
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(12, minmax(0, 1fr));
  padding: 1.25rem;
}

.page-banner > * {
  grid-column: span 12;
}

.banner-callout {
  background: linear-gradient(165deg, rgba(255, 255, 255, 0.72), rgba(231, 244, 242, 0.82));
  border: 1px solid rgba(42, 56, 57, 0.08);
  border-radius: 20px;
  padding: 1rem;
}

.banner-label,
.code-preview-label {
  color: rgba(25, 34, 34, 0.6);
  font-size: 0.76rem;
  font-weight: 700;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}

.banner-copy {
  color: rgba(31, 37, 40, 0.8);
  margin-top: 0.45rem;
}

.layout-grid {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(12, minmax(0, 1fr));
}

.layout-grid > * {
  grid-column: span 12;
}

.split-grid {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.student-card {
  background: rgba(255, 255, 255, 0.35);
  border: 1px solid rgba(42, 56, 57, 0.06);
}

.toolbar-row {
  align-items: center;
  display: grid;
  gap: 1rem;
  grid-template-columns: minmax(0, 1fr);
  margin-bottom: 1rem;
}

.student-name {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 1.15rem;
  font-weight: 700;
}

.student-meta,
.student-total {
  color: rgba(31, 37, 40, 0.72);
}

.score-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 0.6rem;
}

.score-pill {
  align-items: center;
  background: rgba(33, 74, 76, 0.08);
  border-radius: 999px;
  color: #214a4c;
  display: inline-flex;
  gap: 0.6rem;
  padding: 0.45rem 0.9rem;
}

.code-preview {
  background: rgba(22, 33, 34, 0.96);
  border-radius: 18px;
  color: #e6f0ef;
  padding: 1rem;
}

.code-preview pre {
  font-family: 'IBM Plex Mono', 'SFMono-Regular', monospace;
  font-size: 0.85rem;
  margin: 0.75rem 0 0;
  overflow-x: auto;
}

.empty-state {
  align-items: center;
  color: rgba(31, 37, 40, 0.78);
  display: grid;
  gap: 0.45rem;
  justify-items: center;
  min-height: 260px;
  text-align: center;
}

.empty-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 1.1rem;
  font-weight: 700;
}

.empty-copy {
  max-width: 34ch;
}

@media (min-width: 1100px) {
  .page-banner > :first-child {
    grid-column: span 8;
  }

  .page-banner > :last-child {
    grid-column: span 4;
  }

  .layout-grid > :first-child {
    grid-column: span 4;
  }

  .layout-grid > :last-child {
    grid-column: span 8;
  }

  .toolbar-row {
    grid-template-columns: minmax(0, 1fr) auto;
  }
}

@media (max-width: 680px) {
  .split-grid {
    grid-template-columns: 1fr;
  }
}
</style>
