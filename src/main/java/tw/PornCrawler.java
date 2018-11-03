package tw;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tw.PornBot;
import tw.common.Properties;
import tw.dao.PornRecordDao;
import tw.entity.PornRecord;
import tw.service.DownloadService;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("prototype")
public class PornCrawler extends WebCrawler {

    @Autowired
    private PornRecordDao pornRecordDao;

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);
    private static final Pattern VIEWKEY_PATTERN = Pattern.compile("(.*)(viewkey=)(.*[\\&]{0,1})(.*)");
    private static final Pattern p = Pattern.compile("(.*)(var flashvars_.*?=)(.*?language.*?})(.*)(Categories\\:<\\/h3>)(.*?(<\\/div>))(.*)(VIDEO_SHOW = )(.*watched.*?})(.*)");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return url.getURL().contains("?viewkey=") && url.getURL().startsWith("https://www.pornhub.com/");
    }

//    @Override
//    protected WebURL handleUrlBeforeProcess(WebURL webURL) {
////        if(VIEWKEY_PATTERN.matcher(webURL.getURL()).matches()) {
////            WebURL embedUrl = new WebURL();
////            embedUrl.setURL(EMBED_URL + getViewkey(webURL));
////            return embedUrl;
////        } else {
////            return super.handleUrlBeforeProcess(webURL);
////        }
//    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData) {
                String html = ((HtmlParseData) page.getParseData()).getHtml().replaceAll("\n", "");
                if(shouldVisit(page, page.getWebURL()) ) {
                    Matcher htmlMatcher = Pattern.compile("(.*)(var flashvars_.*?=)(.*?language.*?})(.*)(Categories\\:<\\/h3>)(.*?(<\\/div>))(.*)(VIDEO_SHOW = )(.*watched.*?})(.*)").matcher(html);
                    if(htmlMatcher.matches()) {
                        try {
                            String viewKey = getViewkey(page.getWebURL());
                            String videoJson = htmlMatcher.replaceAll("$3");
                            String likeJson = htmlMatcher.replaceAll("$10");
                            String catogory = htmlMatcher.replaceAll("$6");

                            HashMap<String, Object> videoMap = objectMapper.readValue(videoJson, HashMap.class);
                            HashMap<String, Object> likeMap = objectMapper.readValue(likeJson, HashMap.class);

                            PornRecord pornRecord = new PornRecord(videoMap, likeMap, viewKey, Properties.FILE_PATH);

//                            downloadService.download(myController.getConfig(), pornRecord.getVideo240p(), new File(pornRecord.getFilePath()));
                            pornRecordDao.save(pornRecord);

                        } catch (Exception e) {
                            logger.error("Download fail", e);
                            logger.error("html =   " +html );
                        }
                    } else {
                        logger.error("html not matches [{}], html [{}]", page.getWebURL(), html);
                    }
            }
        }
    }

    public String getViewkey(WebURL webURL) {
        if(!VIEWKEY_PATTERN.matcher(webURL.getURL()).matches()){
            return "";
        } else {
            return VIEWKEY_PATTERN.matcher(webURL.getURL()).replaceAll("$3").replace("&", "");
        }
    }

    public String getEmbedKey(WebURL webURL) {
        final Pattern EMBED_PATTERN = Pattern.compile("(\\/embed\\/)(.*)");
        if(!EMBED_PATTERN.matcher(webURL.getPath()).matches()){
            return "";
        } else {
            return EMBED_PATTERN.matcher(webURL.getPath()).replaceAll("$2");
        }
    }

}
