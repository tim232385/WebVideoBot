package tw.service;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.exceptions.PageBiggerThanMaxSizeException;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Created by Tim.Liu on 2018/10/31.
 */
@Service
public class DownloadService {

    private static final Logger logger = LoggerFactory.getLogger(DownloadService.class);

    public void download(CrawlConfig config, String url, File file) throws InterruptedException, IOException {
        PageFetcher pageFetcher = new PageFetcher(config);
        WebURL curURL = new WebURL();
        curURL.setURL(url);
        PageFetchResult fetchResult = null;
        try {
            fetchResult = pageFetcher.fetchPage(curURL);
            if (fetchResult.getStatusCode() == HttpStatus.SC_OK) {
                logger.info("Start download filePath:[{}]", file);
                FileUtils.copyInputStreamToFile(fetchResult.getEntity().getContent(), file);
                logger.info("Download Finish filePath:[{}].", file);
            } else {
                logger.info("Skip download url:[{}], HttpStatus:[{}]", url, fetchResult.getStatusCode());
            }
        } catch (PageBiggerThanMaxSizeException e) {
            logger.debug("PageBiggerThanMaxSizeException", e);
            logger.info("Skip download url:[{}], Out of  MaxDownloadSize", url);
        } finally {
            if (fetchResult != null) {
                fetchResult.discardContentIfNotConsumed();
            }
        }
    }

}
