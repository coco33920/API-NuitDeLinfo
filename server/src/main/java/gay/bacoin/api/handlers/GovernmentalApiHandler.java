package gay.bacoin.api.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gay.bacoin.api.FalseInformation;
import gay.bacoin.api.diseases.Disease;
import gay.bacoin.api.diseases.DiseasesDeserializer;
import gay.bacoin.api.gsonpayloads.Answer;
import gay.bacoin.api.gsonpayloads.Game;
import gay.bacoin.api.gsonpayloads.Payload;

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
    private final HashMap<String, Disease> diseasesMap = new HashMap<>();
    private ArrayList<Disease> allDiseases = new ArrayList<>();


    public GovernmentalApiHandler() {
        instance = this;
        try {
            getAllDiseases();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static GovernmentalApiHandler getInstance() {
        if (instance == null)
            new GovernmentalApiHandler();
        return instance;
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

    public String verify(String body) {
        Answer a = null;
        try {
            a = new Gson().fromJson(body, Answer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean exist = !(a == null) && diseasesMap.containsKey(a.getAnswer());
        Payload p = new Payload(exist);
        if (exist && diseasesMap.containsKey(a.getAnswer())) {
            Game.deleteGame(a.getCode());
            Disease disease = diseasesMap.get(a.getAnswer());
            p.setDescription(disease.getDefinition());
            p.setName(disease.getPreferredTerm());
            p.setCode(disease.getOrphaCode());
            p.setLink("https://wikipedia.org");
            p.setDiscoverer("A scientist, I guess");
            p.setDate("A date, I guess");
        }
        return new Gson().toJson(p);
    }

    public String newGame() {
        Disease d = getRandomDisease();
        FalseInformation f = new FalseInformation(d);
        String first = f.generateFalseInformation();
        String second = f.generateFalseInformation();
        Game g = new Game(d, first, second);
        return new Gson().toJson(g);
    }

    public Disease getRandomDisease() {
        Random r = new Random();
        int randomIndex = r.nextInt(allDiseases.size());
        return allDiseases.get(randomIndex);
    }
}
