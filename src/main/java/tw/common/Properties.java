package tw.common;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.PornBot;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.apache.commons.io.FileUtils.openInputStream;

/**
 * Created by Tim.Liu on 2018/11/1.
 */
public class Properties {
    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);
    public static String WORK_PATH = new File(".").getAbsolutePath().replace(".", "");

    public static String FILE_PATH;
    public static boolean DOWNLOAD_VIDEO;

    public static int MAX_VIDEO_SIZE;
    public static int MIX_VIDEO_SIZE = 104857600;
    public static int MAX_PAGE_SIZE = Integer.MAX_VALUE;
    public static int CONCURRENT_THREAD_SIZE = 10;
    public static AtomicInteger nextPage = new AtomicInteger(1);

    private static URIBuilder uriBuilder = null;
    private static String USER_PROP_FILE_NAME = "config.properties";

    static {
        try {
            java.util.Properties properties = new java.util.Properties();
            if(new File(WORK_PATH + "/" + USER_PROP_FILE_NAME).exists()) {
                properties.load(openInputStream(new File(WORK_PATH + "/" + USER_PROP_FILE_NAME)));
            } else {
                logger.error("config.properties not found use default config");
            }

            MAX_VIDEO_SIZE =  parse(properties, PropertiesParam.VIDEO_DOWNLOAD_SIZE, Integer::valueOf, 104857600);
            MAX_PAGE_SIZE = parse(properties, PropertiesParam.MAX_PAGE_SIZE, Integer::valueOf, 1000);
            CONCURRENT_THREAD_SIZE = parse(properties, PropertiesParam.CONCURRENT_THREAD_SIZE, Integer::valueOf, 10);
            FILE_PATH = parse(properties, PropertiesParam.FILE_PATH, Function.identity(), "D:/video");
            String START_URL = parse(properties, PropertiesParam.START_URL, Function.identity(), "https://www.pornhub.com/video");

            if(!START_URL.startsWith("https://www.pornhub.com/video")) {
                throw new IllegalArgumentException("START_URL need start with https://www.pornhub.com/video");
            }

            uriBuilder = new URIBuilder(START_URL);

            uriBuilder.getQueryParams()
                .stream()
                .filter(param -> param.getName().equalsIgnoreCase("page"))
                .map(NameValuePair::getValue)
                .map(Integer::valueOf)
                .findFirst()
                .ifPresent(nextPage::set);

            if(MAX_VIDEO_SIZE < MIX_VIDEO_SIZE) {
                MAX_VIDEO_SIZE = MIX_VIDEO_SIZE;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static <R> R parse(java.util.Properties properties, PropertiesParam propertiesParam, Function<String, R> fn, R defaultValue) {
        return Optional.ofNullable(properties.get(propertiesParam.name()))
            .filter(o -> o != null)
            .map(Object::toString)
            .map(fn)
            .orElse(defaultValue);
    }

    public static String getNextUrl() throws URISyntaxException {
        URIBuilder uriBuilder = Properties.uriBuilder.setParameter("page", nextPage.getAndAdd(1) + "");
        return uriBuilder.build().toString();
    }
}

