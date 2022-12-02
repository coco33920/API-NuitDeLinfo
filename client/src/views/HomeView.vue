<template>
    <main>
        <div class="card">
            <RouterLink id="btn" to="/Game">Start the game</RouterLink>
        </div>
    </main>
</template>

<style lang="scss">
#btn {
    font-size: 1.2em;
    font-weight: bold;
    font-family: "Inter", sans-serif;
    color: #fff;
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
</style>
