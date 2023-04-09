package gay.bacoin.api.gsonpayloads;

import gay.bacoin.api.diseases.Disease;

import java.util.HashMap;
import java.util.Random;

public class Game {

    private static final HashMap<Integer, Game> allGamesInstances = new HashMap<>();
    private final Disease trueName;
    private final String falseNameOne;
    private final String falseNameTwo;
    private final int gameID;

    public Game(Disease trueName, String falseNameOne, String falseNameTwo) {
        this.trueName = trueName;
        this.falseNameOne = falseNameOne;
        this.falseNameTwo = falseNameTwo;
        Random r = new Random();
        int gameID = r.nextInt(10_000);
        while (allGamesInstances.containsKey(gameID)) {
            gameID = r.nextInt(10_000);
        }
        this.gameID = gameID;
    }

    public static HashMap<Integer, Game> getAllGamesInstances() {
        return allGamesInstances;
    }

    public static void deleteGame(int id) {
        allGamesInstances.remove(id);
    }

    public Disease getTrueName() {
        return trueName;
    }

    public int getGameID() {
        return gameID;
    }

    public String getFalseNameOne() {
        return falseNameOne;
    }

    public String getFalseNameTwo() {
        return falseNameTwo;
    }
}
