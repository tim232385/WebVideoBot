package tw;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class PornCrawlerFactory implements CrawlController.WebCrawlerFactory{

    @Autowired
    private ObjectFactory<PornCrawler> beanFactory;

    @Override
    public WebCrawler newInstance() throws Exception {
        return beanFactory.getObject();
    }

}
