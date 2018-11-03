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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tw.Utils.RandomAgentsUtils;
import tw.Utils.RandomStringUtils;
import tw.common.Properties;

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

    public static void main(String[] args) throws Exception {
        while (true){
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> a = restTemplate.getForEntity("https://www.pornhub.com/embed/ph5afe168457d94", String.class);
            System.out.print(a.toString());
        }
//        ScriptEngineManager factory = new ScriptEngineManager();
//
//        factory.
//        function leastFactor(n) {
//        if (isNaN(n) || !isFinite(n)) return NaN;
//        if (typeof phantom !== 'undefined') return 'phantom';
//        if (typeof module !== 'undefined' && module.exports) return 'node';
//        if (n==0) return 0;
//        if (n%1 || n*n<2) return 1;
//        if (n%2==0) return 2;
//        if (n%3==0) return 3;
//        if (n%5==0) return 5;
//        var m=Math.sqrt(n);
//        for (var i=7;i<=m;i+=30) {
//            if (n%i==0)      return i;
//            if (n%(i+4)==0)  return i+4;
//            if (n%(i+6)==0)  return i+6;
//            if (n%(i+10)==0) return i+10;
//            if (n%(i+12)==0) return i+12;
//            if (n%(i+16)==0) return i+16;
//            if (n%(i+22)==0) return i+22;
//            if (n%(i+24)==0) return i+24;
//        }
//        return n;
//}

//


    }

}
