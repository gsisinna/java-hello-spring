<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useDisplay } from 'vuetify'

const route = useRoute()
const router = useRouter()
const { mdAndUp } = useDisplay()
const drawer = ref(false)

const items = [
  { title: 'Dashboard', value: '/', icon: 'mdi-view-dashboard-outline', caption: 'Overview and quick launch' },
  { title: 'Students', value: '/students', icon: 'mdi-account-group-outline', caption: 'Public JSON flow' },
  { title: 'Courses', value: '/courses', icon: 'mdi-database-outline', caption: 'Secured Mongo CRUD' },
  { title: 'Learn', value: '/learn', icon: 'mdi-book-open-page-variant-outline', caption: 'Architecture map' },
]

const currentPath = computed(() => route.path)
const currentItem = computed(() => items.find((item) => item.value === route.path) ?? items[0]!)

function navigate(path: string) {
  if (route.path !== path) {
    router.push(path)
  }

  if (!mdAndUp.value) {
    drawer.value = false
  }
}

watch(mdAndUp, (isDesktop) => {
  drawer.value = isDesktop
}, { immediate: true })
</script>

<template>
  <v-app>
    <div class="app-backdrop">
      <div class="orb orb-sand"></div>
      <div class="orb orb-teal"></div>
      <div class="mesh"></div>
    </div>

    <v-navigation-drawer
      v-model="drawer"
      app
      :permanent="mdAndUp"
      :temporary="!mdAndUp"
      class="shell-drawer"
      border="0"
      width="286"
    >
      <div class="drawer-inner">
        <div class="brand-lockup" @click="navigate('/')">
          <div class="brand-mark">JS</div>
          <div>
            <div class="brand-title">Java Spring Studio</div>
            <div class="brand-subtitle">Learning repo cockpit</div>
          </div>
        </div>

        <div class="workspace-note">
          Use the UI to trigger backend behavior quickly, then inspect the Java code that produced it.
        </div>

        <div class="nav-stack">
          <button
            v-for="item in items"
            :key="item.value"
            class="nav-card"
            :class="{ 'nav-card--active': currentPath === item.value }"
            type="button"
            @click="navigate(item.value)"
          >
            <v-icon :icon="item.icon" size="22" />
            <div>
              <div class="nav-card-title">{{ item.title }}</div>
              <div class="nav-card-copy">{{ item.caption }}</div>
            </div>
          </button>
        </div>

        <div class="drawer-footer">
          <v-btn
            class="docs-button"
            href="/swagger-ui.html"
            target="_blank"
            rel="noopener"
            prepend-icon="mdi-api"
            variant="flat"
            color="primary"
            block
          >
            Open API Docs
          </v-btn>
          <v-btn
            href="/v3/api-docs.yaml"
            target="_blank"
            rel="noopener"
            prepend-icon="mdi-file-document-outline"
            variant="text"
            block
          >
            Open OpenAPI YAML
          </v-btn>
        </div>
      </div>
    </v-navigation-drawer>

    <v-app-bar app class="shell-bar" flat height="86">
      <template #prepend>
        <v-app-bar-nav-icon v-if="!mdAndUp" @click="drawer = !drawer" />
      </template>

      <div class="topbar-copy">
        <div class="topbar-title">{{ currentItem.title }}</div>
        <div class="topbar-subtitle">{{ currentItem.caption }}</div>
      </div>

      <v-spacer></v-spacer>

      <div class="topbar-actions">
        <v-chip class="soft-chip" prepend-icon="mdi-lightning-bolt-outline" size="small">
          Fast backend feedback
        </v-chip>
      </div>
    </v-app-bar>

    <v-main class="shell-main">
      <div class="shell-content">
        <router-view />
      </div>
    </v-main>
  </v-app>
</template>

<style scoped>
.app-backdrop {
  inset: 0;
  pointer-events: none;
  position: fixed;
  z-index: 0;
}

.mesh {
  background:
    linear-gradient(120deg, rgba(240, 191, 122, 0.09), transparent 40%),
    radial-gradient(circle at 80% 14%, rgba(78, 189, 174, 0.16), transparent 26%),
    radial-gradient(circle at 16% 30%, rgba(232, 132, 96, 0.15), transparent 22%),
    linear-gradient(180deg, #fcf8f1 0%, #f2ece2 100%);
  height: 100%;
  position: absolute;
  width: 100%;
}

.orb {
  border-radius: 999px;
  filter: blur(70px);
  opacity: 0.58;
  position: absolute;
}

.orb-sand {
  background: rgba(227, 170, 95, 0.42);
  height: 16rem;
  left: 7%;
  top: 5rem;
  width: 16rem;
}

.orb-teal {
  background: rgba(84, 184, 172, 0.36);
  height: 20rem;
  right: 4%;
  top: 9rem;
  width: 20rem;
}

.shell-bar,
.shell-main {
  position: relative;
  z-index: 1;
}

.shell-drawer {
  background: rgba(251, 247, 239, 0.78);
  border-right: 1px solid rgba(68, 51, 36, 0.08);
}

.drawer-inner {
  display: grid;
  gap: 1.25rem;
  height: 100%;
  padding: 1.15rem;
}

.shell-bar {
  backdrop-filter: blur(18px);
  background: rgba(251, 247, 239, 0.72);
  border-bottom: 1px solid rgba(68, 51, 36, 0.08);
  padding-inline: 1rem;
}

.brand-lockup {
  align-items: center;
  cursor: pointer;
  display: flex;
  gap: 1rem;
}

.brand-mark {
  align-items: center;
  background:
    linear-gradient(145deg, rgba(36, 71, 72, 0.96), rgba(88, 144, 133, 0.96));
  border-radius: 1.1rem;
  color: #f6f1e9;
  display: grid;
  font-family: 'Space Grotesk', sans-serif;
  font-size: 1.05rem;
  font-weight: 700;
  height: 3rem;
  letter-spacing: 0.1em;
  place-items: center;
  width: 3rem;
}

.brand-title {
  color: #1f2528;
  font-family: 'Space Grotesk', sans-serif;
  font-size: 1.05rem;
  font-weight: 700;
  letter-spacing: 0.01em;
  line-height: 1.2;
}

.brand-subtitle {
  color: rgba(31, 37, 40, 0.68);
  font-size: 0.88rem;
  line-height: 1.3;
}

.workspace-note {
  color: rgba(31, 37, 40, 0.74);
  font-size: 0.94rem;
  line-height: 1.6;
}

.nav-stack {
  display: grid;
  gap: 0.75rem;
}

.nav-card {
  align-items: start;
  background: rgba(255, 255, 255, 0.54);
  border: 1px solid rgba(48, 59, 59, 0.08);
  border-radius: 20px;
  color: #314041;
  cursor: pointer;
  display: grid;
  gap: 0.85rem;
  grid-template-columns: auto 1fr;
  padding: 0.95rem 1rem;
  text-align: left;
  transition: transform 180ms ease, box-shadow 180ms ease, background 180ms ease;
}

.nav-card:hover {
  box-shadow: 0 14px 32px rgba(36, 54, 54, 0.08);
  transform: translateY(-1px);
}

.nav-card--active {
  background: linear-gradient(135deg, rgba(33, 74, 76, 0.94), rgba(74, 141, 134, 0.94));
  color: #f6f2eb;
}

.nav-card-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 1rem;
  font-weight: 700;
  line-height: 1.25;
}

.nav-card-copy {
  font-size: 0.88rem;
  line-height: 1.4;
  opacity: 0.8;
}

.drawer-footer {
  display: grid;
  gap: 0.5rem;
  margin-top: auto;
}

.topbar-copy {
  display: grid;
  gap: 0.12rem;
}

.topbar-title {
  color: #162122;
  font-family: 'Space Grotesk', sans-serif;
  font-size: 1.05rem;
  font-weight: 700;
  line-height: 1.2;
}

.topbar-subtitle {
  color: rgba(31, 37, 40, 0.68);
  font-size: 0.88rem;
  line-height: 1.35;
}

.topbar-actions {
  align-items: center;
  display: flex;
  gap: 0.5rem;
}

.docs-button {
  color: #1d4d4f;
  font-weight: 600;
}

.shell-main {
  margin: 0 auto;
  overflow: visible;
  padding: 1.75rem 0 2.25rem;
}

.shell-content {
  margin: 0 auto;
  max-width: 1500px;
  padding-inline: 1rem;
  padding-top: 0.5rem;
  width: 100%;
}

@media (max-width: 720px) {
  .shell-bar {
    padding-inline: 0.25rem;
  }

  .brand-subtitle {
    display: none;
  }

  .topbar-actions {
    display: none;
  }

  .shell-main {
    padding-top: 1.2rem;
  }
}

@media (min-width: 960px) {
  .shell-main {
    padding-left: 286px;
  }
}
</style>
