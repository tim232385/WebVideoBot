import common.PropertiesParam;
import common.RandomAgents;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tim.Liu on 2018/10/31.
 */
public class PornCrawlControllerFactory {

    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);
    private final static String CRAWL_STORAGE = "/data/crawl/root";

    public CrawlController getController(String startUrl) throws Exception {
        String ran = "";
        for (int j = 0; j < 10; j++) {
            char randomLetter = (char) ('a' + Math.random() * ('z'-'a' + 1));
            ran += randomLetter;
        }
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_STORAGE + "/" + ran);
        config.setDefaultHeaders(PornCrawler.initHeader());
        config.setCookieStore(new PornCookieStore());
        config.setMaxDownloadSize(Integer.valueOf(PornProperties.get(PropertiesParam.MAX_DOWNLOAD_SIZE)));
        config.setPolitenessDelay(1000);
        config.setUserAgentString(RandomAgents.nextAgent());

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        controller.addSeed(startUrl);
        return controller;
    }
}
