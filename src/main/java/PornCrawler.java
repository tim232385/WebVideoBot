import com.google.common.collect.Lists;
import common.Downloader;
import common.PropertiesParam;
import common.RandomAgents;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class PornCrawler extends WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);

    private static final Pattern VIEWKEY_PATTERN = Pattern.compile("(.*)(viewkey=)(.*[\\&]{0,1})(.*)");
    private static final Pattern FIND_VIDEO_PATTERN = Pattern.compile("(.*)(\\\"videoUrl\\\"\\:\\\")(.*)(\\\"\\}\\])(.*)");
    private final static String EMBED_URL = "https://www.pornhub.com/embed/";
    private final static String VIDEO_PATH = PornProperties.get(PropertiesParam.VIDEO_PATH) + "\\";

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return VIEWKEY_PATTERN.matcher(url.getURL()).matches();
    }

    @Override
    protected WebURL handleUrlBeforeProcess(WebURL webURL) {
        CrawlConfig config = myController.getConfig();
        config.setCookieStore(new PornCookieStore());
        config.setDefaultHeaders(initHeader());
        if(VIEWKEY_PATTERN.matcher(webURL.getURL()).matches()) {
            WebURL embedUrl = new WebURL();
            embedUrl.setURL(EMBED_URL + getViewkey(webURL));
            return embedUrl;
        } else {
            return super.handleUrlBeforeProcess(webURL);
        }
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData) {
            String html = ((HtmlParseData) page.getParseData()).getHtml().replaceAll("\n", "");
            if(FIND_VIDEO_PATTERN.matcher(html).matches()){
                String videoUrl = FIND_VIDEO_PATTERN.matcher(html).replaceAll("$3").replace("\\/", "/");
                logger.info("videoUrl == " + videoUrl);
                try {
                    String embedKey = getEmbedKey(page.getWebURL());
                    new Downloader(myController.getConfig()).download(videoUrl, new File(
                        VIDEO_PATH + embedKey + ".mp4"));
                } catch (Exception e) {
                    e.printStackTrace();
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

    public static List<Header> initHeader() {
        Header header = new BasicHeader("User-Agent", RandomAgents.nextAgent());
        return Lists.newArrayList(header);
    }

}
