import { createApp } from "vue";
import KonamiCode from "vue3-konami-code";

// import { createPinia } from "pinia";

import App from "@/App.vue";
import router from "@/router";

const app = createApp(App);

app.use(KonamiCode, {
    onKonamiCodeEntered: function () {
        alert("Konami Code successfully entered!");
    },
});

// app.use(createPinia());
app.use(router);

app.mount("#app");
