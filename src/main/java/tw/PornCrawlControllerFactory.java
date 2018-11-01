package tw;

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

    private static final Logger logger = LoggerFactory.getLogger(PornCrawlControllerFactory.class);
    private final static String CRAWL_STORAGE = "/data/crawl/root";

    public CrawlController getController() throws Exception {
        String ran = "";
        for (int j = 0; j < 10; j++) {
            char randomLetter = (char) ('a' + Math.random() * ('z'-'a' + 1));
            ran += randomLetter;
        }
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_STORAGE + "/" + ran);
        config.setDefaultHeaders(PornCrawler.initHeader());
        config.setCookieStore(new PornCookieStore());
        config.setMaxDownloadSize(PornProperties.MAX_VIDEO_SIZE);
        config.setPolitenessDelay(1000);
        config.setUserAgentString(RandomAgents.nextAgent());
        config.setResumableCrawling(true);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        return controller;
    }
}
