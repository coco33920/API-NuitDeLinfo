package gay.bacoin.api;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gay.bacoin.api.objects.*;
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

    private static HttpClient client = HttpClient.newHttpClient();
    private static final Type listOfDiseasesType = new TypeToken<ArrayList<Disease>>() {
    }.getType();
    private static ArrayList<Disease> allDiseases = new ArrayList<>();
    private static final HashMap<String, Disease> diseasesMap = new HashMap<>();
    private static final ArrayList<String> allDiseasesName = new ArrayList<>();
    private static String LOREM_IPSUM = "\n" +
        "\n" +
        "Aenean rutrum pellentesque hendrerit. Nam elementum augue sapien. Etiam tempus enim a leo porttitor, vel ultricies nibh auctor. Donec lobortis lorem ut enim porta, et fermentum ex pretium. Morbi at viverra metus. Donec enim nulla, laoreet ac iaculis gravida, dapibus tempor justo. Maecenas et condimentum urna. Donec vehicula justo non nibh auctor, vitae varius leo efficitur. Duis eleifend massa sed nisi viverra tristique. Etiam sed erat tortor. Praesent eu volutpat libero, sit amet consequat ligula. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed commodo id quam elementum viverra. Aenean ut orci eu velit fermentum dignissim. Mauris pharetra, odio aliquet condimentum pretium, lorem urna semper urna, ac ultrices massa nulla et mi.\n" +
        "\n" +
        "Sed placerat nisi eget mauris molestie, sit amet vehicula lorem lacinia. Duis tempus eu ex non viverra. Mauris ut libero augue. Fusce pretium placerat nunc vitae lacinia. Donec bibendum odio vel feugiat convallis. Ut massa ligula, porta ut auctor at, condimentum a felis. Fusce sagittis ipsum vitae velit dictum vehicula a molestie tellus. Suspendisse consequat imperdiet ante ut mollis. Duis a mauris vel odio pellentesque tincidunt. Sed commodo vel nibh a vestibulum.\n" +
        "\n" +
        "Phasellus eleifend non urna et lacinia. Aliquam nibh ipsum, tincidunt et ligula sit amet, faucibus gravida lorem. Sed eu felis sed sapien suscipit feugiat porta vitae orci. Sed commodo lorem eget laoreet scelerisque. Duis ac mi tempus, interdum nibh sit amet, vestibulum felis. Donec ipsum purus, gravida a tellus vitae, semper congue dui. Ut non purus placerat, efficitur elit eu, ullamcorper leo. Maecenas aliquet augue non euismod tincidunt. Fusce molestie magna at velit viverra, sed finibus eros pretium. Integer a risus eu arcu condimentum efficitur. In at purus nec purus cursus varius.\n" +
        "\n" +
        "In in odio et dui imperdiet ultrices. Suspendisse in ultricies leo. Aenean nec pretium lectus, eu blandit eros. Mauris vel turpis sed erat sagittis molestie varius vel enim. Maecenas risus tellus, tempus sed eleifend quis, pellentesque ultricies metus. Mauris bibendum rhoncus turpis ut lobortis. Aenean vitae arcu eu elit sagittis dignissim. Duis eget justo faucibus, convallis dui in, pharetra sapien. In id est faucibus, congue nisl a, aliquam tellus. Ut ullamcorper nibh vitae hendrerit molestie. Morbi faucibus purus risus, fermentum consequat enim scelerisque tincidunt. Proin sit amet nulla ornare, fermentum dui eu, elementum mauris. In at sodales turpis. Aenean dapibus purus sem, eget mattis nisi posuere sit amet.\n" +
        "\n" +
        "In condimentum dapibus luctus. Nunc ultrices lobortis quam, non sollicitudin augue. Suspendisse efficitur ex ex, non accumsan risus tristique vel. Ut aliquet lacinia mollis. Donec sit amet diam vitae urna sagittis ornare. Nullam interdum blandit mi id malesuada. Duis metus ligula, maximus ac sollicitudin ac, tempus sit amet massa. Morbi vulputate odio quis turpis posuere hendrerit. In pharetra nulla venenatis, semper diam a, consectetur tellus. Fusce eu accumsan odio. Aliquam erat volutpat. Donec suscipit urna turpis, ultricies ullamcorper justo luctus vitae. Donec elementum auctor nunc, vitae vestibulum elit consequat a. Aenean sed risus efficitur, porta ante ac, tincidunt turpis. Sed sit amet justo a mi pharetra gravida. Vestibulum iaculis risus nisl, non congue neque.";

    private static ArrayList<String> latinWords;

    //TODO: session client

    public static void main(String[] args) throws IOException, InterruptedException {
        ipAddress("0.0.0.0");
        port(8080);
        setupRoute();
        System.out.println("Downloading diseases from the official governmental API");
        getAllDiseases();
        ArrayList<String> words = new ArrayList<>();
        System.out.println("Generating random latin words");
        LOREM_IPSUM.trim()
            .lines()
            .map(s -> s.split(" "))
            .collect(Collectors.toList())
            .stream()
            .map(strings -> Arrays.stream(strings).filter(s -> s.length() > 5).map(s -> {
                if (!StringUtils.isAlpha(s.substring(s.length() - 1))) {
                    s = s.substring(0, s.length() - 1);
                }
                return s;
            }).map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))).forEach(el -> words.addAll(el.collect(Collectors.toList())));
        latinWords = words;
        System.out.println("All ready to go!");
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
        allDiseases.forEach(
            disease -> diseasesMap.put(disease.getPreferredTerm(), disease)
        );
        allDiseases.forEach(el -> allDiseasesName.add(el.getPreferredTerm()));
    }

    private static String generateFalseInformation(Disease d) {
        int i = new Random().nextInt(100);
        String name = d.getPreferredTerm();
        if(name.split(" ").length > 3){
            String[] s = name.split(" ");
            String first = "";
            String last = "";

            if(i <= 20){
                Faker f = new Faker(Locale.US);
                Random r = new Random();
                int randomLatinWordIndex = r.nextInt(latinWords.size());
                first = latinWords.get(randomLatinWordIndex);
                last = f.name().lastName();
                s[0] = first;
                s[s.length - 1] = last;
                return String.join(" ",s);
            }

            if (i <= 33) {
                Faker f = new Faker(Locale.GERMANY);
                first = "Pathologie";
                last = "de " + f.name().lastName();
                s[0] = first;
                s[s.length - 1] = last;
                return String.join(" ",s);
            }

            if (i <= 58) {
                Faker f = new Faker(Locale.US);
                first = "Maladie";
                last = "de " + f.name().lastName();
                s[0] = first;
                s[s.length - 1] = last;
                return String.join(" ",s);
            }

            if (i <= 78) {
                Faker f = new Faker(Locale.UK);
                first = "Syndrome";
                last = "de " + f.name().lastName();
                s[0] = first;
                s[s.length - 1] = last;
                return String.join(" ",s);
            }

            if (i <= 93) {
                Random r = new Random();
                int randomLatinWordIndex = r.nextInt(latinWords.size());
                String randomLatinWord = latinWords.get(randomLatinWordIndex);
                first = "Syndrome";
                last = "de " + randomLatinWord;
                s[0] = first;
                s[s.length - 1] = last;
                return String.join(" ",s);
            }

            Faker f = new Faker(Locale.US);
            Random r = new Random();
            int randomLatinWordIndex = r.nextInt(latinWords.size());
            first = latinWords.get(randomLatinWordIndex);
            last = "sur" + f.animal().name();

            s[0] = first;
            s[s.length - 1] = last;
            return String.join(" ",s);
        }
        if (i <= 20) {
            Faker f = new Faker(Locale.US);
            Random r = new Random();
            int randomLatinWordIndex = r.nextInt(latinWords.size());
            String randomLatinWord = latinWords.get(randomLatinWordIndex);
            return randomLatinWord + " de " + f.name().lastName();
        }

        if (i <= 30) {
            Faker f = new Faker(Locale.GERMANY);
            return "Pathologie de " + f.name().lastName();
        }

        if (i <= 50) {
            Faker f = new Faker(Locale.US);
            return "Maladie de " + f.name().lastName();
        }

        if (i <= 60) {
            Faker f = new Faker(Locale.UK);
            return "Syndrome de " + f.name().lastName();
        }

        if (i <= 75) {
            Random r = new Random();
            int randomLatinWordIndex = r.nextInt(latinWords.size());
            String randomLatinWord = latinWords.get(randomLatinWordIndex);
            return "Syndrome de " + randomLatinWord;
        }

        if (i <= 93) {
            Faker f = new Faker(Locale.US);
            Random r = new Random();
            int randomLatinWordIndex = r.nextInt(latinWords.size());
            String randomLatinWord = latinWords.get(randomLatinWordIndex);
            return randomLatinWord + " de " + f.animal().name();
        }

        Random r = new Random();
        int randomLatinWordIndex = r.nextInt(latinWords.size());
        String randomLatinWord = latinWords.get(randomLatinWordIndex);
        String newRandomLatinWord = randomLatinWord.substring(0, randomLatinWord.length() - 3);
        return newRandomLatinWord + "-virus";
    }

    private static Disease getRandomDisease(){
        Random r = new Random();
        int randomIndex = r.nextInt(allDiseases.size());
        return allDiseases.get(randomIndex);
    }

    private static void setupRoute() {
        get("/", (request, response) -> {
            response.type("application/json");
            return "{\"text\":\"Hello World\"}";
        });
        get("/new_game", ((request, response) -> {
            Disease d = getRandomDisease();
            response.type("application/json");
            String first = generateFalseInformation(d);
            String second = generateFalseInformation(d);
            Game g = new Game(d,first,second);
            return new Gson().toJson(g);
        }));
        post("/verify", ((request, response) -> {
            String body = request.body(); //body : answer: String
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
            response.type("application/json");
            return new Gson().toJson(p);
        }));
    }
}