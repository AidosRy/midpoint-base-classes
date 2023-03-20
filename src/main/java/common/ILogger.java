package common;

/**
 * Logging interface with default methods
 */
public interface ILogger {

    String START = "started";
    String ERROR = "error :";
    String SUCCESS = "success";
    String WARNING = "warning:";

    String CONFIG = "Configuration";
    String CONN = "Connector";

    String brackets = "()";
    String INIT = "init" + brackets;
    String CHECK = "checkAlive" + brackets;
    String VALIDATE = "validate" + brackets;
    String DISPOSE = "dispose" + brackets;
    String DELETE = "delete" + brackets;
    String SYNC = "sync" + brackets;
    String TOKEN = "getLatestSyncToken" + brackets;
    String FILTER = "createFilterTranslator" + brackets;
    String QUERY = "executeQuery" + brackets;
    String UPDATE = "updateDelta" + brackets;
    String CREATE = "create" + brackets;
    String CREATE_OBJECT_HANDLER = "createObjectHandler" + brackets;
    String SCHEMA = "schema" + brackets;
    String TEST = "test" + brackets;
    String AUTH = "authenticate" + brackets;
    String RESOLVE = "resolveUsername" + brackets;
    String SCRIPT_CONN = "runScriptOnConnector" + brackets;
    String SCRIPT_RES = "runScriptOnResource" + brackets;

    String OPEN_CONN = "openConnection" + brackets;

    String OK = "ok";
    String ERR = "error";
}
