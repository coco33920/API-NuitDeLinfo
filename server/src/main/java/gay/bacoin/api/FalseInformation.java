package gay.bacoin.api;

import com.github.javafaker.Faker;
import gay.bacoin.api.diseases.Disease;

import java.util.Locale;
import java.util.Random;

public class FalseInformation {

    private Disease disease;

    public FalseInformation(Disease disease){
        this.disease = disease;
    }


    private String getRandomLatinWord(){
        Random r = new Random();
        int randomLatinWordIndex = r.nextInt(Server.getLatinWords().size());
        return Server.getLatinWords().get(randomLatinWordIndex);
    }

    private String generateFalseInformationIfSizeIsSuperior(int i, String name){
        String[] s = name.split(" ");
        String first = "";
        String last = "";

        if (i <= 20) {
            Faker f = new Faker(Locale.US);
            first = getRandomLatinWord();
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
            last = "de " + getRandomLatinWord();
            s[0] = first;
            s[s.length - 1] = last;
            return String.join(" ", s);
        }

        Faker f = new Faker(Locale.US);
        first = getRandomLatinWord();
        last = "sur" + f.animal().name();

        s[0] = first;
        s[s.length - 1] = last;
        return String.join(" ", s);
    }

    private String generateFalseInformationIfSizeIsInferior(int i){
        if (i <= 20) {
            Faker f = new Faker(Locale.US);
            return getRandomLatinWord() + " de " + f.name().lastName();
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
            return "Syndrome de " + getRandomLatinWord();
        }

        if (i <= 93) {
            Faker f = new Faker(Locale.US);
            return getRandomLatinWord() + " de " + f.animal().name();
        }

        Random r = new Random();
        String randomLatinWord = getRandomLatinWord();
        String newRandomLatinWord = randomLatinWord.substring(0, randomLatinWord.length() - 3);
        return newRandomLatinWord + "-virus";
    }

    public String generateFalseInformation() {
        int i = new Random().nextInt(100);
        String name = disease.getPreferredTerm();
        if (name.split(" ").length > 3) {
            return generateFalseInformationIfSizeIsSuperior(i,name);
        }
        return generateFalseInformationIfSizeIsInferior(i);
    }


}
