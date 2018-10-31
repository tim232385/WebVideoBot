import com.google.common.collect.Lists;
import common.PropertiesParam;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PornBot {

    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);
    private final static String URL = "https://www.pornhub.com/video?page=";
    private final static int MAX_PAGE_SIZE = Integer.valueOf(PornProperties.get(PropertiesParam.MAX_PAGE_SIZE));
    private final static int CONCURRENT_THREAD_SIZE = Integer.valueOf(PornProperties.get(PropertiesParam.CONCURRENT_THREAD_SIZE));
    private static AtomicInteger page = new AtomicInteger(1);

    private static ArrayList<CrawlController> runnig = Lists.newArrayList();

    public static synchronized int getNextPage() {
        return page.getAndAdd(1);
    }

    public static synchronized int getPage() {
        return page.get();
    }

    public static void main(String[] args) throws Exception {
        PornCrawlControllerFactory crawlControllerFactory = new PornCrawlControllerFactory();
        for (int i = page.get(); i < MAX_PAGE_SIZE; ) {
            int size = runnig.size();
            int diffThread = CONCURRENT_THREAD_SIZE - size;
            // 補至CONCURRENT_THREAD_SIZE
            for (int j = 1; j < diffThread; j++) {
                String startUrl = URL + getNextPage();
                CrawlController controller = crawlControllerFactory.getController(startUrl);
                controller.startNonBlocking(PornCrawler.class, 1);
                runnig.add(controller);
            }

            for (CrawlController crawlController : runnig) {
                if(crawlController.isFinished()) {
                    runnig.remove(crawlController);
                    break;
                }
            }
        }
    }
}
