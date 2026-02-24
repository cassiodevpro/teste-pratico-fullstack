

<script setup>
import { RouterView } from 'vue-router';
import { ref } from 'vue';

const darkMode = ref(false);
function toggleTheme() {
  darkMode.value = !darkMode.value;
  document.body.classList.toggle('dark-theme', darkMode.value);
}
</script>

<template>
  <div class="main-layout" :class="{ 'dark': darkMode }">
    <header class="header">
      <div style="display:flex;align-items:center;gap:1rem;">
        <router-link to="/">
          <button class="home-btn">🏠 Início</button>
        </router-link>
        <h1 style="margin:0;">Coffee Cassiodevpro Brasil</h1>
        <button class="theme-btn" @click="toggleTheme">
          <span v-if="!darkMode">🌙 Tema Escuro</span>
          <span v-else>☀️ Tema Claro</span>
        </button>
      </div>
      <nav>
        <router-link to="/raw-materials" class="nav-link" active-class="active">
          Matérias-primas
        </router-link>
        <router-link to="/products" class="nav-link" active-class="active">
          Produtos
        </router-link>
        <router-link to="/optimize" class="nav-link" active-class="active">
          Otimizar
        </router-link>
      </nav>
    </header>
    <main>
      <RouterView />
    </main>
    <footer class="footer">
      <span>☕ Coffee Factory &copy; 2026</span>
    </footer>
  </div>
</template>

<style scoped>
.theme-btn {
  background: #fff;
  color: #6b4f27;
  border: 1px solid #bfa77a;
  border-radius: 6px;
  padding: 0.4rem 1rem;
  font-weight: bold;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s;
  margin-left: 1rem;
}
.theme-btn:hover {
  background: #f5e7c6;
}

body.dark-theme, .main-layout.dark {
  background: #232323 !important;
  color: #f5f3ea !important;
}
.main-layout.dark .header {
  background: #181818 !important;
  color: #f5f3ea !important;
}
.main-layout.dark .footer {
  background: #181818 !important;
  color: #f5f3ea !important;
}
.main-layout.dark .home-btn, .main-layout.dark .theme-btn {
  background: #333 !important;
  color: #f5f3ea !important;
  border: 1px solid #444 !important;
}
.main-layout.dark .nav-link {
  color: #f5f3ea !important;
}
.main-layout.dark .nav-link.active, .main-layout.dark .nav-link:hover {
  background: #333 !important;
}
.home-btn {
  background: #fff;
  color: #6b4f27;
  border: 1px solid #bfa77a;
  border-radius: 6px;
  padding: 0.4rem 1rem;
  font-weight: bold;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s;
  margin-right: 0.5rem;
}
.home-btn:hover {
  background: #f5e7c6;
}
.main-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f5f3ea;
}
.header {
  background: #6b4f27;
  color: #fff;
  padding: 1rem 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.header h1 {
  margin: 0;
  font-family: 'Merriweather', serif;
  font-size: 2rem;
}
.header nav button {
  background: transparent;
  border: none;
  color: #fff;
  font-size: 1rem;
  margin-left: 1rem;
  cursor: pointer;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background 0.2s;
}
/* Ativa o destaque para router-link ativo */
.header nav .nav-link {
  background: transparent;
  border: none;
  color: #fff;
  font-size: 1rem;
  margin-left: 1rem;
  cursor: pointer;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background 0.2s;
  text-decoration: none;
  display: inline-block;
}
.header nav .nav-link.active, .header nav .nav-link:hover {
  background: #8d6748;
}
main {
  flex: 1;
  padding: 2rem;
}
.footer {
  background: #e6e1d3;
  color: #6b4f27;
  text-align: center;
  padding: 1rem;
  font-size: 1rem;
}
</style>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
