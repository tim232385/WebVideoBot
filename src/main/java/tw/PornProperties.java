package tw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Tim.Liu on 2018/11/1.
 */
public class PornProperties {

    private static String propFileName = "config.properties";
    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);
    private static Properties properties = new Properties();

    static {
        InputStream inputStream = PornProperties.class.getClassLoader().getResourceAsStream(propFileName);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.trace("load properties faild, please check config.properties", e);
        }
    }

    public static String get(PropertiesParam propertiesParam) {
        return (String) properties.get(propertiesParam.name());
    }

//    public static <V> V  getOrDefault(tw.common.PropertiesParam propertiesParam, V defaultVaue) {
//        return (V) properties.getOrDefault(propertiesParam.name(), defaultVaue);
//    }

}

