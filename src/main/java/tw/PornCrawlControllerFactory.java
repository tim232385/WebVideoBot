package tw;

import com.google.common.collect.Lists;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.Utils.RandomStringUtils;
import tw.common.Properties;
import tw.Utils.RandomAgentsUtils;

import java.util.List;


/**
 * Created by Tim.Liu on 2018/10/31.
 */
public class PornCrawlControllerFactory {

    private static final Logger logger = LoggerFactory.getLogger(PornCrawlControllerFactory.class);
    private final static String CRAWL_STORAGE = "/data/crawl/root";

    public CrawlController getController() throws Exception {
        CrawlConfig config = prepareConfig();
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        return controller;
    }

    private CrawlConfig prepareConfig() throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_STORAGE + "/" + RandomStringUtils.random(10));
        config.setDefaultHeaders(prepareHeader());
        config.setCookieStore(new PornCookieStore());
        config.setMaxDownloadSize(Properties.MAX_VIDEO_SIZE);
        config.setPolitenessDelay(1000);
        config.setUserAgentString(RandomAgentsUtils.nextAgent());
        config.setResumableCrawling(true);
        return config;
    }

    private List<Header> prepareHeader() {
        Header header = new BasicHeader("User-Agent", RandomAgentsUtils.nextAgent());
        return Lists.newArrayList(header);
    }

}
