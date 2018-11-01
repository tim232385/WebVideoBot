package tw;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Created by Tim.Liu on 2018/11/1.
 */
public class PornProperties {

    private static String userPropFileName = "\\config.properties";
    private static String defultPropFileName = "defaultConfig.properties";
    private static final Logger logger = LoggerFactory.getLogger(PornBot.class);
    private static Properties properties = new Properties();

    static {
        String filePath = new File(System.getProperty("java.class.path")).getAbsoluteFile().getParentFile().toString();
        filePath += userPropFileName;

        InputStream inputStream = null;
        try {
            inputStream = FileUtils.openInputStream(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputStream == null) {
            inputStream = PornProperties.class.getClassLoader().getResourceAsStream(defultPropFileName);
            logger.info("config.properties notFount use default config...");
        } else {
            logger.info("Use config.properties...");
        }

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.trace("load properties faild, please check config.properties", e);
        }
    }

    public static String get(PropertiesParam propertiesParam) {
        return (String) properties.get(propertiesParam.name());
    }

}

