import com.google.common.collect.Lists;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim.Liu on 2018/10/31.
 */
public class PornCookieStore extends BasicCookieStore {

    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);

    @Override
    public synchronized List<Cookie> getCookies() {
        ArrayList<String> objects = Lists.newArrayList("pornhub.com", "www.pornhub.com", ".pornhub.com");
        BasicCookieStore basicCookieStore = new BasicCookieStore();
        for (int i = 0; i < 3; i++) {
            ArrayList<BasicClientCookie> objects1 = Lists.newArrayList();
            String bs = "";
            objects1.add(new BasicClientCookie("ua", "b5b29e4074b1362df9783c4beff7fc0f"));
            objects1.add(new BasicClientCookie("platform", "pc"));
            objects1.add(new BasicClientCookie("RNLBSERVERID", "ded6730"));
            objects1.add(new BasicClientCookie("g36FastPopSessionRequestNumber", "1"));
            objects1.add(new BasicClientCookie("FastPopSessionRequestNumber", "1"));
            objects1.add(new BasicClientCookie("FPSRN", "1"));
            objects1.add(new BasicClientCookie("performance_timing", "home"));
            objects1.add(new BasicClientCookie("RNKEY", "1554611*2565377:3508882876:1824418585:1"));

            for (int j = 0; j < 32; j++) {
                char randomLetter = (char) ('a' + Math.random() * ('z'-'a' + 1));
                bs += randomLetter;
            }
            if (bs.length() != 32) {
                throw new IllegalArgumentException("d");
            }
            objects1.add(new BasicClientCookie("bs", bs));
            for (BasicClientCookie cookie : objects1) {
                cookie.setDomain(objects.get(i));
                cookie.setPath("/");
            }
            objects1.forEach(basicCookieStore::addCookie);
        }
        return basicCookieStore.getCookies();
    }
}
