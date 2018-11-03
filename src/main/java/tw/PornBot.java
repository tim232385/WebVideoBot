package tw;

import com.google.common.collect.Lists;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tw.common.Properties;
import tw.service.DownloadService;

import java.util.ArrayList;

@SpringBootApplication
public class PornBot implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);
    private static ArrayList<CrawlController> runnigCrawl = Lists.newArrayList();

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private PornCrawler PornCrawler;

    @Autowired
    private ObjectFactory<PornCrawlerFactory> pornCrawlerFactory;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PornBot.class);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("PornBot Start");
        PornCrawlControllerFactory crawlControllerFactory = new PornCrawlControllerFactory();
        for (int i = 1; i <= Properties.MAX_PAGE_SIZE;) {
//             補至CONCURRENT_THREAD_SIZE
            for (; runnigCrawl.size() < Properties.CONCURRENT_THREAD_SIZE && i <= Properties.MAX_PAGE_SIZE;) {
                String startUrl = Properties.getNextUrl();
                CrawlController controller = crawlControllerFactory.getController();
                controller.addSeed(startUrl);
                controller.startNonBlocking(pornCrawlerFactory.getObject(), 2);
                runnigCrawl.add(controller);
                logger.info("CrawlController start search url:[{}].", startUrl);
            }

            for (CrawlController crawlController : runnigCrawl) {
                if(crawlController.isFinished()) {
                    runnigCrawl.remove(crawlController);
                    break;
                }
            }
        }
    }


}
