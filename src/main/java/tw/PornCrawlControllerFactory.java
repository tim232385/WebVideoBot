package tw;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.Utils.RandomAgentsUtils;
import tw.Utils.RandomStringUtils;
import tw.common.Properties;


/**
 * Created by Tim.Liu on 2018/10/31.
 */
public class PornCrawlControllerFactory {

    private static final Logger logger = LoggerFactory.getLogger(PornCrawlControllerFactory.class);
    private final static String CRAWL_STORAGE = "/data/crawl/root";
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();

    public CrawlController getController() throws Exception {
        CrawlConfig config = prepareConfig();
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        return controller;
    }

    private CrawlConfig prepareConfig() {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_STORAGE + "/" + RandomStringUtils.random(10));
        config.setCookieStore(new PornCookieStore());
        config.setMaxDownloadSize(Properties.MAX_VIDEO_SIZE);
        config.setUserAgentString(RandomAgentsUtils.nextAgent());
        config.setResumableCrawling(true);
        config.setThreadShutdownDelaySeconds(2);
        config.setThreadMonitoringDelaySeconds(2);
        return config;
    }

}
