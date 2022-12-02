package gay.bacoin.api.objects.disease;

import gay.bacoin.api.WikipediaHandler;

import java.io.IOException;

public class Disease {

    private final String status;
    private final String preferredTerm;
    private final long orphaCode;
    private final String definition;
    private final String date;

    private String wikipediaLink;

    public Disease(String status, String preferredTerm, long orphaCode, String definition, String date, String wikipediaLink) {
        this.status = status;
        this.preferredTerm = preferredTerm;
        this.orphaCode = orphaCode;
        this.definition = definition;
        this.date = date;
        this.wikipediaLink = wikipediaLink;
    }

    public String getStatus() {
        return status;
    }

    public String getPreferredTerm() {
        return preferredTerm;
    }

    public long getOrphaCode() {
        return orphaCode;
    }

    public String getDefinition() {
        return definition;
    }

    public String getDate() {
        return date;
    }

    public void setWikipediaLink(){
        WikipediaHandler wh = new WikipediaHandler(this.preferredTerm);
        try {
            this.wikipediaLink = wh.getWikipediaURL();
        } catch (IOException e) {
            this.wikipediaLink = "https://wikipedia.org/";
        }
    }

    public String getWikipediaLink() {
        return wikipediaLink;
    }

}
