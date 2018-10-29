import com.google.common.collect.Lists;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicHeader;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class WebPornBot {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");
    private final static String URL = "x";

    public static void main(String[] args) throws Exception {
        final int MAX_CRAWL_DEPTH = 1;
        final int NUMBER_OF_CRAWELRS = 2;
        final String CRAWL_STORAGE = "/data/crawl/root";
        List headers = Lists.newArrayList();
        WebPornBot webPornBot = new WebPornBot();
        String agent = webPornBot.randomChoice(webPornBot.getAgents());

        Header header = new BasicHeader("User-Agent", agent);
        headers.add(header);

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_STORAGE);
        config.setMaxDepthOfCrawling(MAX_CRAWL_DEPTH);
        config.setDefaultHeaders(headers);
        CookieStore CookieStore = config.getCookieStore();

        PageFetcher pageFetcher = new PageFetcher(config);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(URL);
        controller.start(MyCrawler.class, NUMBER_OF_CRAWELRS);
    }

    private String randomChoice(String[] collection) {
        int next = new Random().nextInt(collection.length);
        return collection[next];
    }


    private String[] getAgents() {
        return new String[]{
                "Mozilla/5.0 (Windows; U; Win98; en-US; rv:1.4) Gecko Netscape/7.1 (ax)"
        };
    }
}
