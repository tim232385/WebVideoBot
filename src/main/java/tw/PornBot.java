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

import java.awt.*;
import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class PornBot implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);
    private static ArrayList<CrawlController> runnigCrawl = Lists.newArrayList();

    @Autowired
    private ObjectFactory<PornCrawlerFactory> pornCrawlerFactory;

    public static void main(String[] args) {
        SpringApplication.run(PornBot.class);
    }

    @Override
    public void run(String... args) throws Exception {
        openBrowser();

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

    private void openBrowser() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.indexOf("win") >= 0) {
            Runtime rt = Runtime.getRuntime();
            String url = "http://localhost:8000/h2-console/";
            rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else if (os.indexOf("mac") >= 0) {
            Runtime rt = Runtime.getRuntime();
            String url = "http://localhost:8000/h2-console/";
            rt.exec("open " + url);
        } else if (os.indexOf("nix") >=0 || os.indexOf("nux") >=0) {
            Runtime rt = Runtime.getRuntime();
            String url = "http://localhost:8000/h2-console/";
            String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror",
                    "netscape", "opera", "links", "lynx" };

            StringBuffer cmd = new StringBuffer();
            for (int i = 0; i < browsers.length; i++)
                if(i == 0)
                    cmd.append(String.format(    "%s \"%s\"", browsers[i], url));
                else
                    cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
            // If the first didn't work, try the next browser and so on

            rt.exec(new String[] { "sh", "-c", cmd.toString() });
        }
    }
}
