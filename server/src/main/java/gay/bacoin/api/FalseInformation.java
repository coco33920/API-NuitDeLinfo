package gay.bacoin.api;

import com.github.javafaker.Faker;
import gay.bacoin.api.diseases.Disease;
import gay.bacoin.api.handlers.LatinWordHandler;

import java.util.Locale;
import java.util.Random;

public class FalseInformation {

    private final Disease disease;

    public FalseInformation(Disease disease) {
        this.disease = disease;
    }


    private String generateFalseInformationIfSizeIsSuperior(int i, String name) {
        String[] s = name.split(" ");
        String first = "";
        String last = "";

        if (i <= 20) {
            Faker f = new Faker(Locale.US);
            first = LatinWordHandler.getInstance().getRandomLatinWord();
            last = f.name().lastName();
            s[0] = first;
            s[s.length - 1] = last;
            return String.join(" ", s);
        }

        if (i <= 33) {
            Faker f = new Faker(Locale.GERMANY);
            first = "Pathologie";
            last = "de " + f.name().lastName();
            s[0] = first;
            s[s.length - 1] = last;
            return String.join(" ", s);
        }

        if (i <= 58) {
            Faker f = new Faker(Locale.US);
            first = "Maladie";
            last = "de " + f.name().lastName();
            s[0] = first;
            s[s.length - 1] = last;
            return String.join(" ", s);
        }

        if (i <= 78) {
            Faker f = new Faker(Locale.UK);
            first = "Syndrome";
            last = "de " + f.name().lastName();
            s[0] = first;
            s[s.length - 1] = last;
            return String.join(" ", s);
        }

        if (i <= 93) {
            first = "Syndrome";
            last = "de " + LatinWordHandler.getInstance().getRandomLatinWord();
            s[0] = first;
            s[s.length - 1] = last;
            return String.join(" ", s);
        }

        Faker f = new Faker(Locale.US);
        first = LatinWordHandler.getInstance().getRandomLatinWord();
        last = "sur" + f.animal().name();

        s[0] = first;
        s[s.length - 1] = last;
        return String.join(" ", s);
    }

    private String generateFalseInformationIfSizeIsInferior(int i) {
        if (i <= 20) {
            Faker f = new Faker(Locale.US);
            return LatinWordHandler.getInstance().getRandomLatinWord() + " de " + f.name().lastName();
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
            return "Syndrome de " + LatinWordHandler.getInstance().getRandomLatinWord();
        }

        if (i <= 93) {
            Faker f = new Faker(Locale.US);
            return LatinWordHandler.getInstance().getRandomLatinWord() + " de " + f.animal().name();
        }

        Random r = new Random();
        String randomLatinWord = LatinWordHandler.getInstance().getRandomLatinWord();
        String newRandomLatinWord = randomLatinWord.substring(0, randomLatinWord.length() - 3);
        return newRandomLatinWord + "-virus";
    }

    public String generateFalseInformation() {
        int i = new Random().nextInt(100);
        String name = disease.getPreferredTerm();
        if (name.split(" ").length > 3) {
            return generateFalseInformationIfSizeIsSuperior(i, name);
        }
        return generateFalseInformationIfSizeIsInferior(i);
    }


}
