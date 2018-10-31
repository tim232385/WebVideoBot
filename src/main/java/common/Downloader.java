package common;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.exceptions.PageBiggerThanMaxSizeException;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;

/**
 * Created by Tim.Liu on 2018/10/31.
 */
public class Downloader {

    private PageFetcher pageFetcher;

    public Downloader(CrawlConfig config) {
        pageFetcher = new PageFetcher(config);
    }

    public void download(String url, File file) throws InterruptedException, PageBiggerThanMaxSizeException, IOException {
        WebURL curURL = new WebURL();
        curURL.setURL(url);
        PageFetchResult fetchResult = null;
        try {
            fetchResult = pageFetcher.fetchPage(curURL);
            if (fetchResult.getStatusCode() == HttpStatus.SC_OK) {
                FileUtils.copyInputStreamToFile(fetchResult.getEntity().getContent(), file);
            }
        } finally {
            if (fetchResult != null) {
                fetchResult.discardContentIfNotConsumed();
            }
        }
    }
}
