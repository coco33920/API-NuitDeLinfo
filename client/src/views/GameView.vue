<script>
import axios from "axios";
import { defineComponent } from "vue";

export default defineComponent({
    data: function () {
        return {
            fact: "",
            value: false,
            explanation: "",
            title: null,
            card_info: null,
        };
    },
    mounted() {
        this.card_info = document.getElementById("info_card");

        this.set_question();
    },
    methods: {
        set_question() {
            this.card_info.classList.remove("show");

            axios.get("https://api.nwa2coco.fr/request").then((response) => {
                let r = response.data;

                this.fact = r.fact;
                this.value = r.value;
                this.explanation = r.explanation;
            });
        },
        reveal(guess) {
            if (guess === this.value) {
                this.title = "Bravo!";
            } else {
                this.title = "Incorrect!";
            }
            this.card_info.classList.add("show");
        },
    },
});
</script>
<template>
    <div class="btn_container">
        <RouterLink class="goto_ld" to="/">Acceuil</RouterLink>
    </div>
    <main>
        <div id="info_card" class="card">
            <p>{{ fact }}</p>
            <p>{{ title }}</p>
            <p>{{ explanation }}</p>

            <div id="ic_bottom">
                <button @click="set_question()">Continuer le jeu</button>
            </div>
        </div>

        <div id="card" class="card">
            <div class="card_bg"></div>
            <p>{{ fact }}</p>
            <div id="answer">
                <button @click="reveal(false)">False</button>
                <button @click="reveal(true)">True</button>
            </div>
        </div>
    </main>
</template>

<style lang="scss" scoped>
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

#answer {
    display: flex;
    gap: 0.5em;
}

#card {
    max-width: 800px;
}

button {
    font-size: 14px;
    font-family: "Inter", sans-serif;
    font-weight: 500;
    background: #a4241f;
    border-radius: 16px;
    border: none;
    color: white;
    padding: 1em 2em;
    text-align: left;
    transition: 0.2s ease-in-out;

    &:hover {
        color: #fba9a7;
    }
}

p {
    font-weight: bold;
    font-family: "Inter", sans-serif;
    color: #fff;
    padding-bottom: 1em;
}

#info_card {
    display: none;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 99999999;
    outline: 4px solid #a4241f;
    transition: 1s ease-in-out;

    &::before {
        content: "";
        position: absolute;
        inset: -1000%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: -1;
    }

    p:nth-child(2) {
        font-size: 0.7em;
        font-weight: normal;
    }
}

#info_card.show {
    display: block;
}

#info_card {
    & > p:nth-child(1) {
        font-size: 1.5rem;
    }
    & > p:nth-child(2) {
        font-weight: bold;
        font-size: 1.5rem;
    }
    & > p:nth-child(3) {
        font-size: 1rem;
    }
}
</style>
