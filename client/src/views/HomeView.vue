<script setup>
// set message to request output of localhost:8080

import { ref } from 'vue'

const message = ref('')

fetch('http://localhost:8080')
    .then(response => response.json())
    .then(text => message.value = text.text)
</script>

<template>
  <main>
    <p><code>{{ message }}</code></p>
  </main>
</template>
