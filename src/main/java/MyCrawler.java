import com.google.common.io.Files;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class MyCrawler extends WebCrawler {

    private static final Pattern imgPatterns = Pattern.compile("(.*)(viewkey=)(.*)");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (imgPatterns.matcher(href).matches()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();

        logger.info("URL : {}", url);

        String extension = url.substring(url.lastIndexOf('.'));
        String filename = "C:\\Users\\Tim\\Desktop\\x\\" + "xxx.txt";

        try {
            Files.write(page.getContentData(), new File(filename));
        } catch (IOException iox) {
            logger.error("Failed to write file: " + filename, iox);
        }
    }

}
