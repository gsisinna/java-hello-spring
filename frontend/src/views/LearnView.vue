<template>
  <div class="page-shell">
    <div>
      <div class="section-kicker">Learning map</div>
      <h1 class="section-title">Use the frontend to connect backend concepts to visible behavior.</h1>
      <p class="section-copy mt-4">
        The goal of this UI is not to hide the backend. It is to make the backend easier to inspect.
        Each section below maps a frontend action to the part of the Spring repo that is responsible
        for it.
      </p>
    </div>

    <div class="learn-grid">
      <v-card class="glass-panel" rounded="xl">
        <v-card-title>Student flow</v-card-title>
        <v-card-text class="d-grid ga-3">
          <div class="mapping-row">
            <strong>UI screen:</strong>
            <span>`/students`</span>
          </div>
          <div class="mapping-row">
            <strong>Backend controller:</strong>
            <span>`StudentController`</span>
          </div>
          <div class="mapping-row">
            <strong>Backend service:</strong>
            <span>`StudentService`</span>
          </div>
          <div class="mapping-row">
            <strong>Main learning goal:</strong>
            <span>request models, response models, object state, lists, maps</span>
          </div>
        </v-card-text>
      </v-card>

      <v-card class="glass-panel" rounded="xl">
        <v-card-title>Course flow</v-card-title>
        <v-card-text class="d-grid ga-3">
          <div class="mapping-row">
            <strong>UI screen:</strong>
            <span>`/courses`</span>
          </div>
          <div class="mapping-row">
            <strong>Input port:</strong>
            <span>`CourseService`</span>
          </div>
          <div class="mapping-row">
            <strong>Use case:</strong>
            <span>`CourseApplicationService`</span>
          </div>
          <div class="mapping-row">
            <strong>Output port / adapter:</strong>
            <span>`CourseStore` -> `MongoCourseStore`</span>
          </div>
          <div class="mapping-row">
            <strong>Main learning goal:</strong>
            <span>clean architecture, MongoDB persistence, secured CRUD</span>
          </div>
        </v-card-text>
      </v-card>

      <v-card class="glass-panel" rounded="xl">
        <v-card-title>What to change next</v-card-title>
        <v-card-text class="d-grid ga-3">
          <div class="idea-pill">Add a course filter endpoint and mirror it in the UI.</div>
          <div class="idea-pill">Add delete/update support for students and surface it here.</div>
          <div class="idea-pill">Expand auth from Basic Auth to roles or JWT.</div>
          <div class="idea-pill">Add frontend tests for the forms and API states.</div>
        </v-card-text>
      </v-card>
    </div>

    <v-card class="glass-panel" rounded="xl">
      <v-card-title>Reference links</v-card-title>
      <v-card-text class="d-flex flex-wrap ga-3">
        <v-btn href="/swagger-ui.html" target="_blank" rel="noopener" color="primary">
          Swagger UI
        </v-btn>
        <v-btn href="/v3/api-docs.yaml" target="_blank" rel="noopener" variant="outlined">
          OpenAPI YAML
        </v-btn>
        <v-btn href="https://vuejs.org/" target="_blank" rel="noopener" variant="text">
          Vue docs
        </v-btn>
        <v-btn href="https://vuetifyjs.com/" target="_blank" rel="noopener" variant="text">
          Vuetify docs
        </v-btn>
      </v-card-text>
    </v-card>

    <v-card class="glass-panel" rounded="xl">
      <v-card-title>Recommended study loop</v-card-title>
      <v-card-text class="study-grid">
        <div class="study-step">
          <div class="study-number">1</div>
          <div>
            <strong>Open a screen in this frontend.</strong>
            Start with `Students` for simple request and response models.
          </div>
        </div>
        <div class="study-step">
          <div class="study-number">2</div>
          <div>
            <strong>Trigger the API behavior.</strong>
            Create or edit records and observe the visible state change.
          </div>
        </div>
        <div class="study-step">
          <div class="study-number">3</div>
          <div>
            <strong>Read the backend class behind it.</strong>
            Jump from the UI concept to the controller, service, or store.
          </div>
        </div>
        <div class="study-step">
          <div class="study-number">4</div>
          <div>
            <strong>Modify code and retest immediately.</strong>
            Use Swagger when you need to compare UI behavior with the raw contract.
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<style scoped>
.learn-grid {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(12, minmax(0, 1fr));
}

.learn-grid > * {
  grid-column: span 12;
}

.mapping-row {
  display: grid;
  gap: 0.35rem;
}

.idea-pill {
  background: rgba(33, 74, 76, 0.06);
  border-radius: 18px;
  color: #22484a;
  padding: 0.9rem 1rem;
}

.study-grid {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(12, minmax(0, 1fr));
}

.study-step {
  align-items: start;
  display: grid;
  gap: 0.8rem;
  grid-column: span 12;
  grid-template-columns: auto 1fr;
}

.study-number {
  align-items: center;
  background: linear-gradient(135deg, #214a4c, #4a8d86);
  border-radius: 999px;
  color: #f7f2ea;
  display: grid;
  font-family: 'Space Grotesk', sans-serif;
  font-weight: 700;
  height: 2.1rem;
  place-items: center;
  width: 2.1rem;
}

@media (min-width: 1100px) {
  .learn-grid > * {
    grid-column: span 4;
  }

  .study-step {
    grid-column: span 6;
  }
}
</style>
