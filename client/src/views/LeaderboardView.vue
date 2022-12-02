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
