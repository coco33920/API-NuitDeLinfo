<script>
import { defineComponent } from "vue";

export default defineComponent({
    components: {},
    methods: {
        check(index, checked) {
            console.log(index, checked);
        },
    },
});
</script>

<template>
    <main>
        <div class="score">
            <h2><span>Score:</span><span>5</span></h2>
        </div>

        <div class="card">
            <p>Laquelle de ces maladies existe?</p>
            <div id="answer">
                <button>aaa</button>
                <button>bbb</button>
                <button>ccc</button>
            </div>
        </div>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
            <path
                fill="#a4241f"
                fill-opacity="1"
                d="M0,288L48,272C96,256,192,224,288,197.3C384,171,480,149,576,165.3C672,181,768,235,864,250.7C960,267,1056,245,1152,250.7C1248,256,1344,288,1392,304L1440,320L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"
            ></path>
        </svg>
        <svg id="foo"  xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
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

.card {
    z-index: 999;
    display: grid;
    padding: 2em;
    background: #57120f;
    border-radius: 16px;
    box-shadow: #a4241f 0 5px 0 0;
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
        margin-left: 2em;
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
</style>
