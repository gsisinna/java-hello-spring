<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useCoursesStore } from '@/stores/courses'
import { useSessionStore } from '@/stores/session'
import type { CourseLevel, CreateCourseRequest } from '@/types/api'

const coursesStore = useCoursesStore()
const sessionStore = useSessionStore()

const credentials = reactive({
  username: sessionStore.username,
  password: sessionStore.password,
})

const createForm = reactive<CreateCourseRequest>({
  title: '',
  level: 'BEGINNER',
  durationInHours: 4,
  published: true,
})

const editorOpen = ref(false)
const editingId = ref<string | null>(null)
const editForm = reactive<CreateCourseRequest>({
  title: '',
  level: 'BEGINNER',
  durationInHours: 4,
  published: true,
})

const levelOptions: CourseLevel[] = ['BEGINNER', 'INTERMEDIATE', 'ADVANCED']
const filters = reactive({
  query: '',
  level: 'ALL',
  publishedOnly: false,
})

onMounted(async () => {
  if (!coursesStore.courses.length && sessionStore.hasCredentials) {
    await connect()
  }
})

const unpublishedCourses = computed(
  () => coursesStore.courses.length - coursesStore.publishedCourses,
)

const filteredCourses = computed(() => {
  return coursesStore.courses.filter((course) => {
    const matchesQuery = [course.title, course.id, course.level]
      .join(' ')
      .toLowerCase()
      .includes(filters.query.toLowerCase())
    const matchesLevel = filters.level === 'ALL' || course.level === filters.level
    const matchesPublished = !filters.publishedOnly || course.published
    return matchesQuery && matchesLevel && matchesPublished
  })
})

const courseHealthLabel = computed(() => {
  if (coursesStore.error) {
    return 'Attention needed'
  }

  if (!coursesStore.courses.length) {
    return 'Not synced'
  }

  return 'Catalog loaded'
})

async function connect() {
  sessionStore.updateCredentials(credentials.username, credentials.password)
  await coursesStore.loadCourses(sessionStore.username, sessionStore.password)
}

async function createCourseRecord() {
  await coursesStore.addCourse(createForm, sessionStore.username, sessionStore.password)
  resetCreateForm()
}

function startEdit(id: string, title: string, level: CourseLevel, durationInHours: number, published: boolean) {
  editingId.value = id
  editForm.title = title
  editForm.level = level
  editForm.durationInHours = durationInHours
  editForm.published = published
  editorOpen.value = true
}

async function saveEdit() {
  if (!editingId.value) {
    return
  }

  await coursesStore.saveCourse(
    editingId.value,
    editForm,
    sessionStore.username,
    sessionStore.password,
  )

  editorOpen.value = false
}

async function remove(id: string) {
  await coursesStore.removeCourse(id, sessionStore.username, sessionStore.password)
}

function resetCreateForm() {
  createForm.title = ''
  createForm.level = 'BEGINNER'
  createForm.durationInHours = 4
  createForm.published = true
}
</script>

<template>
  <div class="page-shell">
    <div class="page-banner glass-panel">
      <div class="section-kicker">Secured course workspace</div>
      <h1 class="section-title">Work directly with the Mongo-backed course module.</h1>
      <p class="section-copy mt-4">
        This screen is designed for the part of the repo that uses stronger architecture. It lets
        you authenticate once, inspect the course catalog, and test create, update, and delete flows
        against the backend.
      </p>
      <div class="banner-status-row mt-6">
        <v-chip class="soft-chip" prepend-icon="mdi-shield-lock-outline">
          Basic Auth required
        </v-chip>
        <v-chip class="soft-chip" prepend-icon="mdi-database-outline">
          {{ courseHealthLabel }}
        </v-chip>
      </div>
    </div>

    <div class="metric-grid">
      <v-card class="glass-panel metric-card">
        <div class="metric-label">Published courses</div>
        <div class="metric-value">{{ coursesStore.publishedCourses }}</div>
        <div class="metric-note">Quick signal for public vs draft learning content</div>
      </v-card>

      <v-card class="glass-panel metric-card">
        <div class="metric-label">Draft courses</div>
        <div class="metric-value">{{ unpublishedCourses }}</div>
        <div class="metric-note">Good for checking filter logic in the UI</div>
      </v-card>

      <v-card class="glass-panel metric-card">
        <div class="metric-label">Last sync</div>
        <div class="metric-value">{{ coursesStore.lastLoadedAt ?? 'Not loaded' }}</div>
        <div class="metric-note">The frontend stores the last successful secured fetch</div>
      </v-card>
    </div>

    <div class="courses-grid">
      <div class="left-column">
        <v-card class="glass-panel" rounded="xl">
          <v-card-title>Authenticate and sync</v-card-title>
          <v-card-subtitle>
            Defaults match the backend learning credentials.
          </v-card-subtitle>
          <v-card-text>
            <div class="d-grid ga-4">
              <v-text-field v-model="credentials.username" label="Username" />
              <v-text-field v-model="credentials.password" label="Password" type="password" />
              <div class="helper-copy">
                Credentials are stored in local browser storage so you can reconnect quickly.
              </div>

              <v-alert v-if="coursesStore.error" type="warning" variant="tonal">
                {{ coursesStore.error }}
              </v-alert>

              <div class="d-flex flex-wrap ga-3">
                <v-btn
                  color="primary"
                  prepend-icon="mdi-sync"
                  :loading="coursesStore.loading"
                  @click="connect"
                >
                  Connect to courses API
                </v-btn>
                <v-btn href="/swagger-ui.html" target="_blank" rel="noopener" variant="outlined">
                  Open Swagger UI
                </v-btn>
              </div>
            </div>
          </v-card-text>
        </v-card>

        <v-card class="glass-panel" rounded="xl">
          <v-card-title>Create a course</v-card-title>
          <v-card-subtitle>
            Posts to the secured `/api/courses` endpoint using Basic Auth.
          </v-card-subtitle>
          <v-card-text>
            <v-form class="d-grid ga-4" @submit.prevent="createCourseRecord">
              <v-text-field v-model="createForm.title" label="Course title" required />
              <v-select v-model="createForm.level" :items="levelOptions" label="Level" />
              <v-text-field
                v-model.number="createForm.durationInHours"
                label="Duration (hours)"
                type="number"
                min="1"
              />
              <v-switch v-model="createForm.published" color="primary" inset label="Published" />

              <div class="d-flex flex-wrap ga-3">
                <v-btn
                  color="secondary"
                  prepend-icon="mdi-plus-circle-outline"
                  :loading="coursesStore.saving"
                  type="submit"
                >
                  Create course
                </v-btn>
                <v-btn variant="text" @click="resetCreateForm">Reset</v-btn>
              </div>
            </v-form>
          </v-card-text>
        </v-card>
      </div>

      <v-card class="glass-panel" rounded="xl">
        <v-card-title>Course catalog</v-card-title>
        <v-card-subtitle>
          CRUD operations mapped to the Spring controller and Mongo-backed service flow.
        </v-card-subtitle>
        <v-card-text>
          <div class="catalog-toolbar">
            <v-text-field
              v-model="filters.query"
              label="Search by title, id, or level"
              prepend-inner-icon="mdi-magnify"
              hide-details
            />
            <v-select
              v-model="filters.level"
              :items="['ALL', ...levelOptions]"
              label="Level"
              hide-details
            />
            <v-switch
              v-model="filters.publishedOnly"
              color="primary"
              inset
              hide-details
              label="Published only"
            />
          </div>

          <v-progress-linear
            v-if="coursesStore.loading"
            indeterminate
            color="primary"
            rounded
          />

          <div v-else-if="!coursesStore.courses.length" class="empty-state">
            <v-icon icon="mdi-lock-check-outline" size="40" />
            <div class="empty-title">Connect to load the secured course catalog.</div>
            <div class="empty-copy">
              Use the credentials on the left, then sync the API to inspect Mongo-backed records.
            </div>
          </div>

          <div v-else-if="!filteredCourses.length" class="empty-state">
            <v-icon icon="mdi-database-search-outline" size="40" />
            <div class="empty-title">No courses match this filter.</div>
            <div class="empty-copy">
              Relax the level or text filter to bring records back into view.
            </div>
          </div>

          <div v-else class="d-grid ga-4">
            <v-card
              v-for="course in filteredCourses"
              :key="course.id"
              class="course-card"
              rounded="xl"
              variant="tonal"
            >
              <v-card-text>
                <div class="d-flex justify-space-between flex-wrap ga-3">
                  <div>
                    <div class="course-title">{{ course.title }}</div>
                    <div class="course-meta">
                      {{ course.level }} · {{ course.durationInHours }} hours · {{ course.id }}
                    </div>
                  </div>

                  <v-chip :color="course.published ? 'success' : 'warning'" size="small">
                    {{ course.published ? 'published' : 'draft' }}
                  </v-chip>
                </div>

                <div class="d-flex flex-wrap ga-2 mt-4">
                  <v-btn
                    color="primary"
                    variant="outlined"
                    prepend-icon="mdi-pencil-outline"
                    @click="startEdit(course.id, course.title, course.level, course.durationInHours, course.published)"
                  >
                    Edit
                  </v-btn>
                  <v-btn
                    color="error"
                    variant="text"
                    prepend-icon="mdi-delete-outline"
                    @click="remove(course.id)"
                  >
                    Delete
                  </v-btn>
                </div>
              </v-card-text>
            </v-card>
          </div>
        </v-card-text>
      </v-card>
    </div>

    <v-dialog v-model="editorOpen" max-width="560">
      <v-card class="glass-panel" rounded="xl">
        <v-card-title>Edit course</v-card-title>
        <v-card-text class="d-grid ga-4">
          <v-text-field v-model="editForm.title" label="Course title" />
          <v-select v-model="editForm.level" :items="levelOptions" label="Level" />
          <v-text-field
            v-model.number="editForm.durationInHours"
            label="Duration (hours)"
            type="number"
            min="1"
          />
          <v-switch v-model="editForm.published" color="primary" inset label="Published" />
        </v-card-text>
        <v-card-actions class="pa-4">
          <v-btn variant="text" @click="editorOpen = false">Cancel</v-btn>
          <v-spacer></v-spacer>
          <v-btn color="primary" :loading="coursesStore.saving" @click="saveEdit">Save changes</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<style scoped>
.courses-grid {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(12, minmax(0, 1fr));
}

.page-banner {
  padding: 1.25rem;
}

.banner-status-row {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem;
}

.courses-grid > * {
  grid-column: span 12;
}

.left-column {
  display: grid;
  gap: 1rem;
}

.course-card {
  background: rgba(255, 255, 255, 0.35);
  border: 1px solid rgba(42, 56, 57, 0.06);
}

.course-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 1.15rem;
  font-weight: 700;
}

.course-meta {
  color: rgba(31, 37, 40, 0.72);
}

.helper-copy {
  color: rgba(31, 37, 40, 0.66);
  font-size: 0.9rem;
}

.catalog-toolbar {
  display: grid;
  gap: 1rem;
  grid-template-columns: minmax(0, 1fr);
  margin-bottom: 1rem;
}

.empty-state {
  align-items: center;
  color: rgba(31, 37, 40, 0.78);
  display: grid;
  gap: 0.45rem;
  justify-items: center;
  min-height: 280px;
  text-align: center;
}

.empty-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 1.12rem;
  font-weight: 700;
}

.empty-copy {
  max-width: 36ch;
}

@media (min-width: 1120px) {
  .courses-grid > :first-child {
    grid-column: span 4;
  }

  .courses-grid > :last-child {
    grid-column: span 8;
  }

  .catalog-toolbar {
    grid-template-columns: minmax(0, 1fr) 170px auto;
  }
}
</style>
