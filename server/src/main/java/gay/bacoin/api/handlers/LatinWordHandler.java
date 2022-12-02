package gay.bacoin.api.handlers;

import gay.bacoin.api.Server;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class LatinWordHandler {

    private static LatinWordHandler instance;
    private final ArrayList<String> latinWords = new ArrayList<>();
    public LatinWordHandler(){
        instance = this;
        generateLatinWords();
    }

    private void generateLatinWords(){
        String LOREM_IPSUM = "\n" +
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
            }).map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))).forEach(el -> latinWords.addAll(el.collect(Collectors.toList())));
    }

    public ArrayList<String> getLatinWords() {
        return latinWords;
    }

    public String getRandomLatinWord(){
        Random r = new Random();
        int randomLatinWordIndex = r.nextInt(latinWords.size());
        return latinWords.get(randomLatinWordIndex);
    }

    public static LatinWordHandler getInstance() {
        if(instance == null)
            new LatinWordHandler();
        return instance;
    }
}
