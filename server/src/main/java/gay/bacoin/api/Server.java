package gay.bacoin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gay.bacoin.api.objects.Disease;
import gay.bacoin.api.objects.DiseasesDeserializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class Server {

    private static HttpClient client = HttpClient.newHttpClient();
    private static final Type listOfDiseasesType = new TypeToken<ArrayList<Disease>>() {}.getType();
    private static ArrayList<Disease> allDiseases = new ArrayList<>();
    public static void main(String[] args) throws IOException, InterruptedException {
        ipAddress("127.0.0.1");
        port(8080);
        setupRoute();
        getAllDiseases();
    }

    private static void getAllDiseases() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.orphacode.org/FR/ClinicalEntity"))
            .header("accept", "application/json")
            .header("apiKey", "coco33920")
            .GET()
            .build();
        GsonBuilder gson = new GsonBuilder().registerTypeAdapter(Disease.class, new DiseasesDeserializer());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        allDiseases = gson.create().fromJson(response.body(), listOfDiseasesType);
        allDiseases = allDiseases.stream()
            .filter(disease -> !disease.getDefinition().equalsIgnoreCase("None available")).collect(Collectors.toCollection(ArrayList::new));
    }

    private static void setupRoute(){
        get("/", (request, response) -> {
            response.type("application/json");
            return "{\"text\":\"Hello World\"}";
        });
        get("/new_game", ((request, response) -> {
            return "Hello world";
        }));
    }
}