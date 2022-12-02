package gay.bacoin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gay.bacoin.api.gsonpayloads.*;
import gay.bacoin.api.diseases.Disease;
import gay.bacoin.api.diseases.DiseasesDeserializer;
import gay.bacoin.api.handlers.DatabaseHandler;
import gay.bacoin.api.handlers.GovernmentalApiHandler;
import gay.bacoin.api.handlers.LatinWordHandler;
import gay.bacoin.api.handlers.WikipediaHandler;
import gay.bacoin.api.vih.Fact;
import gay.bacoin.api.vih.VIHFacts;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class Server {

    public static void main(String[] args) {
        ipAddress("127.0.0.1");
        port(8080);
        setupRoute();
        System.out.println("Downloading diseases from the official governmental API");
        GovernmentalApiHandler.getInstance();
        System.out.println("Generating random latin words");
        LatinWordHandler.getInstance();
        System.out.println("All ready to go!");
        DatabaseHandler.getInstance();
        System.out.println("Database ready :)");
    }

    private static void setupRoute() {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });

        get("/", (request, response) -> {
            response.type("application/json");
            return "{\"text\":\"Hello World\"}";
        });
        get("/new_game", ((request, response) ->
            GovernmentalApiHandler.getInstance().newGame()));
        post("/verify", ((request, response) -> {
            String body = request.body(); //body : answer: String
            return GovernmentalApiHandler.getInstance().verify(body);
        }));
        post("/score", (request, response) -> {
            String body = request.body();
            return DatabaseHandler.getInstance().addScoreIntoDatabase(body);
        });
        get("/leaderboard", (request, response) -> {
            ArrayList<Score> s = DatabaseHandler.getInstance().getLeaderBoard();
            response.type("application/json");
            return new Gson().toJson(s);
        });
        get("/wiki/:name", (request, response) -> {
            WikipediaHandler wh = new WikipediaHandler(request.params(":name"));
            String link = wh.getWikipediaURL();
            response.redirect(link);
            return "";
        });
        get("/request", (request, response) -> {
            Fact f = VIHFacts.getInstance().getARandomFact();
            return new Gson().toJson(f);
        });
    }

    public static String capitalizeString(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }


}