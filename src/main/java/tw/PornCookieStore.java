package tw;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.Utils.RandomStringUtils;
import tw.Utils.RnkeyUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Tim.Liu on 2018/10/31.
 */
public class PornCookieStore extends BasicCookieStore {

    private static final Logger logger = LoggerFactory.getLogger(PornCookieStore.class);

    @Override
    public synchronized List<Cookie> getCookies() {
        BasicCookieStore basicCookieStore = new BasicCookieStore();

        Stream.of("pornhub.com", "www.pornhub.com", ".pornhub.com")
                .map(this::prepareBasicClientCookie)
                .flatMap(List::stream)
                .map(Cookie.class::cast)
                .forEach(basicCookieStore::addCookie);

        return basicCookieStore.getCookies();
    }

    List<BasicClientCookie> prepareBasicClientCookie(String domain) {
        return Stream.of(
                new BasicClientCookie("bs", RandomStringUtils.random(32)),
                new BasicClientCookie("platform", "pc"),
                new BasicClientCookie("ua", "b5b29e4074b1362df9783c4beff7fc0f"),
                new BasicClientCookie("RNLBSERVERID", "ded6646"),
                new BasicClientCookie("g36FastPopSessionRequestNumber", "1"),
                new BasicClientCookie("FastPopSessionRequestNumber", "1"),
                new BasicClientCookie("FPSRN", "1"),
                new BasicClientCookie("performance_timing", "home"),
                new BasicClientCookie("RNKEY", RnkeyUtils.nextKey()))

            .peek(c -> c.setDomain(domain))
            .peek(c -> c.setPath("/"))
            .collect(Collectors.toList());
    }



}
