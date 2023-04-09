package gay.bacoin.api.handlers;

import gay.bacoin.api.Server;
import gay.bacoin.api.utils.ExampleHelpers;
import org.wikidata.wdtk.datamodel.interfaces.Sites;
import org.wikidata.wdtk.dumpfiles.DumpProcessingController;

import java.io.IOException;

public class WikipediaHandler {
    private final String name;

    public WikipediaHandler(String name) {
        this.name = name;
    }

    public String getWikipediaURL() throws IOException {
        DumpProcessingController dumpProcessingController = new DumpProcessingController(
            "wikidatawiki");
        dumpProcessingController.setOfflineMode(ExampleHelpers.OFFLINE_MODE);

        String[] s = this.getName().split(" ");
        s[0] = Server.capitalizeString(s[0]);
        for (int i = 1; i < s.length; i++) {
            s[i] = s[i].toLowerCase();
        }

        String n = String.join(" ", s);

        // Download the sites table dump and extract information
        Sites sites = dumpProcessingController.getSitesInformation();
        return sites.getPageUrl("frwiki", n);
    }

    public String getName() {
        return name;
    }
}
