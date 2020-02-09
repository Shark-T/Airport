package bl.utils;

import org.apache.log4j.Logger;

/**
 * Created by SHARK on 09.08.2017.
 */
public class Log {
    private final static Logger instance = Logger.getLogger(Log.class.getName());

    private Log() {
    }

    public static Logger getInstance() {

        return instance;
    }
}
