package gay.bacoin.api.gsonpayloads;

public class Payload {

    private String description;
    private String date;
    private String discoverer;
    private String link;
    private String name;
    private long code;
    private final boolean exist;

    public Payload(String description, String date, String discoverer, String link, boolean exist, long code, String name) {
        this.description = description;
        this.date = date;
        this.discoverer = discoverer;
        this.link = link;
        this.exist = exist;
        this.code = code;
        this.name = name;
    }

    public Payload(boolean exist) {
        this.exist = exist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiscoverer() {
        return discoverer;
    }

    public void setDiscoverer(String discoverer) {
        this.discoverer = discoverer;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isExist() {
        return exist;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
