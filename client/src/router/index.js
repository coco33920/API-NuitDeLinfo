import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "home",
            component: HomeView,
        },
        {
            path: "/leaderboard",
            name: "leaderboard",
            component: () => import("@/views/LeaderboardView.vue"),
        },
    ],
});

export default router;
