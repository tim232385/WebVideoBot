package tw;

import com.google.common.collect.Lists;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class PornBot {

    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);

    private static ArrayList<CrawlController> runnigCrawl = Lists.newArrayList();

    public static void main(String[] args) throws Exception {
        logger.info("PornBot Start");
        PornCrawlControllerFactory crawlControllerFactory = new PornCrawlControllerFactory();
        for (int i = 1; i <= PornProperties.MAX_PAGE_SIZE; ) {
            // 補至CONCURRENT_THREAD_SIZE
            for (; runnigCrawl.size() < PornProperties.CONCURRENT_THREAD_SIZE && i <= PornProperties.MAX_PAGE_SIZE;) {
                String startUrl = PornProperties.getNextUrl();
                CrawlController controller = crawlControllerFactory.getController();
                controller.addSeed(startUrl);
                controller.startNonBlocking(PornCrawler.class, 10);
                runnigCrawl.add(controller);
                logger.info("CrawlController start getUrl:[{}].", startUrl);
                i++;
            }

            for (CrawlController crawlController : runnigCrawl) {
                if(crawlController.isFinished()) {
                    runnigCrawl.remove(crawlController);
                    break;
                }
                Thread.sleep(1000);
            }
        }
    }
}
