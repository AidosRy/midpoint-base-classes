package common;

public enum OperationType {
    CREATE(ILogger.CREATE),
    UPDATE(ILogger.UPDATE),
    QUERY(ILogger.QUERY),
    DELETE(ILogger.DELETE),
    SYNC(ILogger.SYNC),
    TOKEN(ILogger.TOKEN),
    CREATE_FILTER(ILogger.FILTER),
    INIT(ILogger.INIT),
    DISPOSE(ILogger.DISPOSE),
    CHECK(ILogger.CHECK),
    SCHEMA(ILogger.SCHEMA),
    TEST(ILogger.TEST),
    AUTH(ILogger.AUTH),
    RESOLVE_USERNAME(ILogger.RESOLVE),
    SCRIPT_CONNECTOR(ILogger.SCRIPT_CONN),
    SCRIPT_RESOURCE(ILogger.SCRIPT_RES)
    ;

    private final String operation;

    OperationType(final String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return operation;
    }
}
