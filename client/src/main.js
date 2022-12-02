import { createApp } from "vue";
import KonamiCode from "vue3-konami-code";

// import { createPinia } from "pinia";

import App from "@/App.vue";
import router from "@/router";

import "@/assets/base.scss";

const app = createApp(App);

app.use(KonamiCode, {
    onKonamiCodeEntered: function () {
        console.log("Konami code entered!");
        router.push("/minigame");
    },
});

// app.use(createPinia());
app.use(router);

app.mount("#app");
