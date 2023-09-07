package common.util.properties;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperty {

    private static Logger LOGGER = LoggerFactory.getLogger(ApplicationProperty.class);
    
    private static Properties APP_PROPERTIES = new Properties();

	public ApplicationProperty(String propertiesFilePath) {
        try {
            APP_PROPERTIES.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static String get(String key) {
	    String value = APP_PROPERTIES.getProperty(key);
//	    LOGGER.debug("********************************* key ("+key+") : " + value);
		return value;
	}

	public static int getInt(String key) {
		return Integer.parseInt(APP_PROPERTIES.getProperty(key));
	}

	public static boolean getBoolean(String key) {
	    return Boolean.valueOf(APP_PROPERTIES.getProperty(key)).booleanValue();
	}

}
