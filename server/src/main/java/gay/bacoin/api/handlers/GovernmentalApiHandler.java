package gay.bacoin.api.handlers;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gay.bacoin.api.diseases.Disease;
import gay.bacoin.api.diseases.DiseasesDeserializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

public class GovernmentalApiHandler {

    private static GovernmentalApiHandler instance;
    private final HttpClient client = HttpClient.newHttpClient();
    private final Type listOfDiseasesType = new TypeToken<ArrayList<Disease>>() {
    }.getType();
    private ArrayList<Disease> allDiseases = new ArrayList<>();
    private final HashMap<String, Disease> diseasesMap = new HashMap<>();


    public GovernmentalApiHandler() {
        instance = this;
        try {
            getAllDiseases();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllDiseases() throws IOException, InterruptedException {
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
        allDiseases.forEach(
            disease -> diseasesMap.put(disease.getPreferredTerm(), disease)
        );
    }

    public Disease getRandomDisease() {
        Random r = new Random();
        int randomIndex = r.nextInt(allDiseases.size());
        return allDiseases.get(randomIndex);
    }

    public HashMap<String, Disease> getDiseasesMap() {
        return diseasesMap;
    }

    public static GovernmentalApiHandler getInstance() {
        if (instance == null)
            new GovernmentalApiHandler();
        return instance;
    }
}
