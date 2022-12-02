<script>
import axios from "axios";
import { defineComponent } from "vue";

axios.get("http://localhost:8080/leaderboard").then((response) => {
    console.log(response.data);
});

export default defineComponent({
    components: {},
    data: function () {
        return {
            scores: [],
        };
    },
    // add on mounted
    mounted() {
        axios.get("http://localhost:8080/leaderboard").then((response) => {
            this.scores = response.data;
        });
    },
});
</script>
<template>
    <main>
        <div class="btn_container">
            <RouterLink class="goto_ld" to="/">Acceuil</RouterLink>
        </div>
        <div class="card">
            <h1>Leaderboard</h1>
            <div class="inside" v-for="score in scores">
                <p>{{ score.username }}</p>
                <p>{{ score.score }}</p>
            </div>
        </div>
    </main>
</template>

<style lang="scss">
.btn_container {
    display: flex;
    gap: 1em;
    position: absolute;
    top: 0;
    right: 0;
    margin: 1em;
}

.goto_ld {
    font-size: 14px;
    font-family: "Inter", sans-serif;
    font-weight: 500;
    background: #57120f;
    border-radius: 16px;
    border: none;
    color: white;
    padding: 1em;
    text-align: left;
    transition: 0.2s ease-in-out;
    z-index: 1000;
    text-decoration: none;
}

main {
    overflow: hidden;
    display: grid;
    place-content: center;
    min-height: 100vh;
    font-size: 2em;
}

.card {
    color: white;
    z-index: 999;
    display: grid;
    padding: 2em;
    background: #57120f;
    border-radius: 16px;
    box-shadow: #a4241f 0 5px 0 0;
    transition: none;
}

h1 {
    font-size: 1.2em;
    font-weight: bold;
    font-family: "Inter", sans-serif;
    color: #fff;
    padding-bottom: 1em;
}

.inside {
    display: flex;
    width: 800px;
    align-content: center;
    justify-content: space-between;

    & > p {
        font-size: 0.8em;
        font-weight: bold;
        font-family: "Inter", sans-serif;
        color: #fff;
    }
}
</style>
