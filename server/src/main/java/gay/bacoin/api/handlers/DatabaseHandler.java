package gay.bacoin.api.handlers;

import com.google.gson.Gson;
import gay.bacoin.api.database.DatabaseLite;
import gay.bacoin.api.gsonpayloads.Score;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHandler {

    private static DatabaseHandler instance;
    private DatabaseLite databaseLite;

    public DatabaseHandler(){
        instance = this;
        File homeDb = getHomeDatabaseFile();
        this.databaseLite = new DatabaseLite(homeDb.getAbsolutePath());
        setupDatabase();
    }

    private File getHomeDatabaseFile() {
        String home = System.getProperty("user.home");
        String delimiter = File.separator;
        File f = new File(home + delimiter + ".gaybacoin" + delimiter);
        if (!f.exists())
            f.mkdirs();
        File file = new File(f.getAbsolutePath() + delimiter + "home.db");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Database file loaded to " + file.getAbsolutePath());
        return file;
    }

    private void setupDatabase() {
        String sql = "create table if not exists leaderboard(id integer constraint id primary key autoincrement ,pseudo text,score int);";
        databaseLite.update(sql);
    }

    private void addScoreInDatabase(Score s) {
        String u = String.format("insert into leaderboard(pseudo,score) VALUES(\"%s\",%d)", s.getUsername(), s.getScore());
        databaseLite.update(u);
    }

    public String addScoreIntoDatabase(String score){
        Score s;
        try {
            s = new Gson().fromJson(score, Score.class);
            if (s.getUsername() == null) {
                return "{\"success\":false}";
            }
            addScoreInDatabase(s);
            return "{\"success\":true}";
        } catch (Exception e) {
            return "{\"success\":false}";
        }
    }

    public ArrayList<Score> getLeaderBoard() {
        String sql = "select * from leaderboard SORT ORDER BY score DESC LIMIT 10";
        ArrayList<Score> s = new ArrayList<>();
        ResultSet rs = databaseLite.getResult(sql);
        while (true) {
            try {
                if (!rs.next()) break;
                Score sv = new Score(rs.getString("pseudo"), rs.getInt("score"));
                s.add(sv);
            } catch (SQLException e) {
                return s;
            }
        }
        return s;
    }

    public static DatabaseHandler getInstance() {
        if(instance == null)
            new DatabaseHandler();
        return instance;
    }


}
