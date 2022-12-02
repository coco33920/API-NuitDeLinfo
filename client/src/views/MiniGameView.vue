<script>
import axios from "axios";
import { defineComponent } from "vue";

export default defineComponent({
    components: {},
    data: function () {
        return {
            score: 0,
            game_id: undefined,
            entries: [],
            card: null,
            card_info: null,
            card_info_title: null,
            card_info_description: null,
            card_wiki_link: null,
        };
    },
    // add on mounted
    mounted() {
        this.card = document.getElementById("card");
        this.card_info = document.getElementById("info_card");

        console.log("setup");
        this.setup();
    },
    methods: {
        check(index, checked) {
            console.log(index, checked);
        },
        setup() {
            axios.get("http://localhost:8080/new_game").then((response) => {
                let r = response.data;
                console.log(r);

                this.game_id = r.gameId;
                this.entries = [
                    r.trueName.preferredTerm,
                    r.falseNameOne,
                    r.falseNameTwo,
                ];
                // shuffle entries
                this.entries.sort(() => Math.random() - 0.5);

                console.log(this.entries);
                console.log(`==> ${r.trueName.preferredTerm}`);

                this.card_info_title = r.trueName.preferredTerm;
                this.card_info_description = r.trueName.definition;
            });
        },

        validate(message) {
            axios
                .post("http://localhost:8080/verify", {
                    code: this.game_id,
                    answer: message,
                })
                .then((response) => {
                    if (!response.data.exist) {
                        let name = prompt(
                            "Vous avez perdu.\nEntrez votre nom:"
                        );
                        this.card.classList.add("sink");
                        setTimeout(() => {
                            this.card.classList.remove("sink"), 2000;
                        });
                        axios
                            .post("http://localhost:8080/score", {
                                username: name,
                                score: this.score,
                            })
                            .then((response) => {
                                console.log(response);
                                this.score = 0;
                            });
                        this.setup();
                    } else {
                        this.score++;
                        this.card_info.classList.add("show");
                    }
                });
        },
    },
});
</script>

<template>
    <div class="btn_container">
        <a class="goto_ld" target="_blank" href="/leaderboard">Leaderboard</a>
        <RouterLink class="goto_ld" to="/">Acceuil</RouterLink>
    </div>
    <main>
        <div class="score">
            <h2>
                <span>Score:</span><span>{{ score }}</span>
            </h2>
        </div>

        <div id="info_card" class="card">
            <p>{{ card_info_title }}</p>
            <p>{{ card_info_description }}</p>

            <div id="ic_bottom">
                <button
                    @click="
                        this.card_info.classList.remove('show') || this.setup()
                    "
                >
                    Continuer le jeu
                </button>
                <a
                    target="_blank"
                    :href="'http://localhost:8080/wiki/' + this.card_info_title"
                    >see more</a
                >
            </div>
        </div>

        <div id="card" class="card">
            <div class="card_bg"></div>
            <p>Laquelle de ces maladies existe?</p>
            <div id="answer">
                <button @click="validate(this.entries[0])">
                    {{ this.entries[0] }}
                </button>
                <button @click="validate(this.entries[1])">
                    {{ this.entries[1] }}
                </button>
                <button @click="validate(this.entries[2])">
                    {{ this.entries[2] }}
                </button>
            </div>
        </div>

        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
            <path
                fill="#a4241f"
                fill-opacity="1"
                d="M0,288L48,272C96,256,192,224,288,197.3C384,171,480,149,576,165.3C672,181,768,235,864,250.7C960,267,1056,245,1152,250.7C1248,256,1344,288,1392,304L1440,320L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"
            ></path>
        </svg>
        <svg id="foo" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
            <path
                fill="#a4241f"
                fill-opacity="1"
                d="M0,288L48,272C96,256,192,224,288,197.3C384,171,480,149,576,165.3C672,181,768,235,864,250.7C960,267,1056,245,1152,250.7C1248,256,1344,288,1392,304L1440,320L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"
            ></path>
        </svg>
    </main>
</template>

<style lang="scss" scoped>
main {
    overflow: hidden;
    display: grid;
    place-content: center;
    min-height: 100vh;
    font-size: 2em;
}

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

.card {
    z-index: 999;
    display: grid;
    padding: 2em;
    background: #57120f;
    border-radius: 16px;
    box-shadow: #a4241f 0 5px 0 0;
    transition: none;
}

.card_bg {
    position: absolute;
    inset: 0;
    background: #57120f;
    border-radius: 16px;
    box-shadow: #a4241f 0 5px 0 0;
}

.flip {
    transform: rotateY(360deg);
    transition: 1s ease-in-out;
}

.sink {
    opacity: 0;
    transform: translateY(100vh);
    transition: 1s ease-in-out;
}

p {
    font-weight: bold;
    font-family: "Inter", sans-serif;
    color: #fff;
    padding-bottom: 1em;
}

#answer {
    display: grid;
    gap: 1em;
}

button {
    font-size: 14px;
    font-family: "Inter", sans-serif;
    font-weight: 500;
    background: #a4241f;
    border-radius: 16px;
    border: none;
    color: white;
    padding: 1em;
    text-align: left;
    transition: 0.2s ease-in-out;

    &:hover {
        color: #fba9a7;
    }
}

svg {
    z-index: 999;
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100vh;
    height: auto;
}

svg#foo {
    overflow: hidden;
    transform: rotate(180deg) translateX(-7em);
    right: 0;
    left: auto;
    top: 0;
}

.score {
    position: absolute;
    margin: 1em;

    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1em;
    h2 {
        font-size: 0.9em;
        display: flex;
        justify-content: center;
        align-items: center;
        font-family: "Inter", sans-serif;
        gap: 0.2em;
        span {
            font-weight: bold;
        }
    }
}

#ic_bottom {
    display: flex;
    align-items: center;
    padding-bottom: 0;
    gap: 1em;

    & > a {
        font-size: 14px;
        font-weight: normal;
        font-family: "Inter", sans-serif;
        background: #a4241f;
        border-radius: 16px;
        border: none;
        color: white;
        padding: 1em;
        text-align: left;
        text-decoration: none;
        transition: 0.2s ease-in-out;
    }
}
</style>
