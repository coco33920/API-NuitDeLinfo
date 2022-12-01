package gay.bacoin.api.objects;

public class Disease {

    private final String status;
    private final String preferredTerm;
    private final long orphaCode;
    private final String definition;
    private final String date;

    public Disease(String status, String preferredTerm, long orphaCode, String definition, String date) {
        this.status = status;
        this.preferredTerm = preferredTerm;
        this.orphaCode = orphaCode;
        this.definition = definition;
        this.date = date;
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
}
