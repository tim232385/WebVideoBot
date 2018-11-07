package tw;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.Utils.RnkeyUtils;
import tw.common.Properties;
import tw.dao.PornRecordDao;
import tw.entity.PornRecord;
import tw.service.DownloadService;

import javax.script.ScriptException;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Optional;
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
        if((url.getURL().startsWith("https://www.pornhub.com/")||url.getURL().startsWith("http://www.pornhub.com/")) && url.getURL().contains("viewkey=")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected WebURL handleUrlBeforeProcess(WebURL webURL) {
        return getViewkey(webURL)
                .map(key -> "https://www.pornhub.com/embed/" + key)
                .map(url -> {
                    WebURL newUrl = new WebURL();
                    newUrl.setURL(url);
                    return newUrl;
                }).orElse(super.handleUrlBeforeProcess(webURL));
    }

    @Override
    public void visit(Page page) {
        if(page.getWebURL().getURL().contains("")) {
            String html = ((HtmlParseData) page.getParseData()).getHtml().replaceAll("\n", "");
            Matcher htmlMatcher = Pattern.compile("(.*)(var flashvars.*?)(\\{.*language.*?})(.*)").matcher(html);
            if(htmlMatcher.matches()) {
                logger.info("Find video url:[{}]", page.getWebURL().getURL());
                try {
                    String viewKey = getEmbedKey(page.getWebURL());
                    String videoJson = htmlMatcher.replaceAll("$3");
                    HashMap<String, Object> videoMap = objectMapper.readValue(videoJson, HashMap.class);

                    PornRecord pornRecord = new PornRecord(videoMap, viewKey, Properties.FILE_PATH, Properties.DOWNLOAD_VIDEO);
                    pornRecordDao.save(pornRecord);
                    if(Properties.DOWNLOAD_VIDEO) {
                        downloadService.download(myController.getConfig(), pornRecord.getVideoUrl(), new File(pornRecord.getFilePath()));
                    }
                } catch (Exception e) {
                    logger.error("Download fail", e);
                }
            } else if(Pattern.compile("(.*)(function leastFactor\\(n\\).*)(function go\\(\\) \\{ )(.*)(n=leastFactor\\(p\\);\\{)(.*?=)(.*?;)(.*)").matcher(html).matches()) {
                try {
                    RnkeyUtils.genRnKey(html);
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Optional<String> getViewkey(WebURL webURL) {
        try {
            return new URIBuilder(webURL.getURL())
                    .getQueryParams()
                    .stream()
                    .filter(param -> param.getName().equalsIgnoreCase("viewkey"))
                    .map(NameValuePair::getValue)
                    .findFirst();
        } catch (URISyntaxException e) {
            logger.error("getViewkey", e);
            return Optional.empty();
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
