package common;

import org.identityconnectors.common.logging.Log;

/**
 * Logging class used in static methods
 */
public class Logger implements ILogger{

    String systemName = "";

    public final Log log = Log.getLog(this.getClass());

    public void ok(String funcname, String message) { log.ok("{0}: {1} | {2}", systemName, funcname, message); }

    public void ok(String funcname) { log.ok("{0}: {1}", systemName, funcname); }

    public void info(String funcname, String message) { log.info("{0}: {1} | {2}", systemName, funcname, message); }

    public void info(String funcname) { log.info("{0}: {1}", systemName, funcname); }

    public void warn(String funcname, String message) { log.warn("{0}: {1} | {2}", systemName, funcname, message); }

    public void warn(String funcname) { log.warn("{0}: {1}", systemName, funcname); }

    public void error(String funcname, String message) { log.error("{0}: {1} | {2}", systemName, funcname, message); }

    public void error(String funcname, String message, Exception e) { log.error("{0}: {1} | {2} \n StackTrace: {3}", systemName, funcname, message, e.getStackTrace()); }

    public void error(String funcname) { log.error("{0}: {1}", systemName, funcname); }

    public void error(String funcname, Exception e) {
        log.error("{0}: {1} | Error: {2} \n StackTrace: {3}", systemName, funcname, e.getMessage(), e.getStackTrace()[0]);
        for (var stack : e.getStackTrace()) {
            log.error(stack.toString());
        }
    }

    public String buildString(String... strings) {
        StringBuilder sb = new StringBuilder();
        for (String str : strings) {
            sb.append(str);
            sb.append(" ");
        }
        return sb.toString();
    }

    public String buildString(Object... objects) {
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            if (object == null) {
                sb.append("Object = ").append("null,");
                continue;
            }
            if (!(object instanceof String))
                sb.append(object.getClass().getSimpleName()).append(" = ");
            sb.append(object);
            sb.append(", ");
        }
        return sb.toString();
    }

}
