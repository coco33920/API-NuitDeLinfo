package gay.bacoin.api.objects.disease;

import com.google.gson.*;
import gay.bacoin.api.WikipediaHandler;

import java.io.IOException;
import java.lang.reflect.Type;

public class DiseasesDeserializer implements JsonDeserializer<Disease> {
    @Override
    public Disease deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String status = object.get("Status").getAsString();
        String preferredTerm = object.get("Preferred term").getAsString();
        long orpha = object.get("ORPHAcode").getAsLong();
        String def = "None available";
        try {
            def = object.get("Definition").getAsString();
        } catch (Exception ignored) {

        }
        String date = object.get("Date").getAsString();
        return new Disease(status, preferredTerm, orpha, def, date, "https://wikipedia.org");
    }
}
