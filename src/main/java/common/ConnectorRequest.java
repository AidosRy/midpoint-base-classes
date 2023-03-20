package common;

import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.identityconnectors.framework.spi.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ConnectorRequest extends Logger {
    private final ObjectClass objectClass;
    private final Uid uid;
    private final OperationOptions options;
    private final Filter query;
    private final ResultsHandler resultsHandler;
    private final Set<AttributeDelta> modifications;
    private final Set<Attribute> createAttributes;
    private final SyncToken token;
    private final SyncResultsHandler syncHandler;
    private final Configuration configuration;
    private final GuardedString password;
    private final ScriptContext scriptContext;
    private final String username;

    //custom fields
    private final OperationType operationType;

    public ConnectorRequest(ObjectClass objectClass, Uid uid, OperationOptions options, Filter query, ResultsHandler resultsHandler, Set<AttributeDelta> modifications, Set<Attribute> createAttributes, OperationType operationType, SyncToken token, SyncResultsHandler syncHandler, Configuration configuration, GuardedString password, ScriptContext scriptContext, String username) {
        this.objectClass = objectClass;
        this.uid = uid;
        this.options = options;
        this.query = query;
        this.resultsHandler = resultsHandler;
        this.modifications = modifications;
        this.createAttributes = createAttributes;
        this.operationType = operationType;
        this.token = token;
        this.syncHandler = syncHandler;
        this.configuration = configuration;
        this.password = password;
        this.scriptContext = scriptContext;
        this.username = username;
    }

    public static ConnectorRequestBuilder builder() {
        return new ConnectorRequestBuilder();
    }

    public String getStartMessage() {
        String message = buildString(START);
        switch (operationType) {

            case CREATE:
                return buildString(message, objectClass, createAttributes, options);

            case UPDATE:
                return buildString(message, objectClass, uid, modifications, options);

            case DELETE:
                return buildString(message, objectClass, uid, options);

            case QUERY:
                return buildString(message, objectClass, query, resultsHandler, options);

            case SYNC:
                return buildString(message, objectClass);

            case TOKEN:
                return buildString(message, objectClass);

            case CREATE_FILTER:
                return buildString(message, options, objectClass);

            case TEST:
                return message;

            case CHECK:
                return message;

            case DISPOSE:
                return message;

            case INIT:
                return message;

            case SCHEMA:
                return message;

            case AUTH:
                return buildString(message, objectClass, username, password, options);

            case SCRIPT_RESOURCE:
                return buildString(message, scriptContext, options);

            case SCRIPT_CONNECTOR:
                return buildString(message, scriptContext, options);

            case RESOLVE_USERNAME:
                return buildString(message, objectClass, username, options);

            default:
                throw new RuntimeException(buildString("Incorrect operation type provided:", operationType, ". Possible operations:", Arrays.toString(OperationType.values())));
        }
    }

    public String getSuccessMessage(ConnectorResponse response) {
        String message = buildString(SUCCESS);
        switch (operationType) {

            case CREATE:
                return buildString(message, response.getUid(), createAttributes);

            case UPDATE:
                return buildString(message, response.getModifications(), uid, modifications);

            case DELETE:
                return buildString(message, uid, response);

            case QUERY:
                return buildString(message, query);

            case SYNC:
                return buildString(message, objectClass);

            case TOKEN:
                return buildString(message, response.getToken());

            case CREATE_FILTER:
                return buildString(message, options, response.getFilterTranslator());

            case TEST:
                return message;

            case CHECK:
                return message;

            case DISPOSE:
                return message;

            case INIT:
                return buildString(message, configuration);

            case SCHEMA:
                return buildString(message, response.getSchema());

            case AUTH:
                return buildString(message, response.getUid(), username);

            case SCRIPT_RESOURCE:
                return buildString(message, response.getScriptResponse(), scriptContext);

            case SCRIPT_CONNECTOR:
                return buildString(message, response.getScriptResponse(), scriptContext);

            case RESOLVE_USERNAME:
                return buildString(message, response.getUid(), username);

            default:
                throw new RuntimeException(buildString("Incorrect operation type provided:", operationType, ". Possible operations:", Arrays.toString(OperationType.values())));
        }
    }

    public String getExceptionMessage(Exception e) {
        String message = buildString(ERROR, e.getMessage());
        switch (operationType) {

            case CREATE:
                return buildString(message, objectClass);

            case UPDATE:
                return buildString(message, modifications, objectClass);

            case DELETE:
                return buildString(message, objectClass);

            case QUERY:
                return buildString(message, query, objectClass);

            case SYNC:
                return buildString(token, syncHandler, options, objectClass);

            case TOKEN:
                return buildString(message, token, objectClass);

            case CREATE_FILTER:
                return buildString(message, options, objectClass);

            case TEST:
                return message;

            case CHECK:
                return message;

            case DISPOSE:
                return message;

            case INIT:
                return message;

            case SCHEMA:
                return message;

            case AUTH:
                return buildString(message, objectClass, username, password, options);

            case SCRIPT_RESOURCE:
                return buildString(message, scriptContext, options);

            case SCRIPT_CONNECTOR:
                return buildString(message, scriptContext, options);

            case RESOLVE_USERNAME:
                return buildString(message, objectClass, username, options);

            default:
                return message;
        }
    }

    public String getUsername() {
        return username;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public GuardedString getPassword() {
        return password;
    }

    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    public ObjectClass getObjectClass() {
        return objectClass;
    }

    public Uid getUid() {
        return uid;
    }

    public OperationOptions getOptions() {
        return options;
    }

    public Filter getQuery() {
        return query;
    }

    public ResultsHandler getResultsHandler() {
        return resultsHandler;
    }

    public Set<AttributeDelta> getModifications() {
        return modifications;
    }

    public Set<Attribute> getCreateAttributes() {
        return createAttributes;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public SyncToken getToken() {
        return token;
    }

    public SyncResultsHandler getSyncHandler() {
        return syncHandler;
    }

    public static class ConnectorRequestBuilder {
        private ObjectClass objectClass;
        private Uid uid;
        private OperationOptions options;
        private Filter query;
        private ResultsHandler resultsHandler;
        private Set<AttributeDelta> modifications;
        private Set<Attribute> createAttributes;
        private OperationType operationType;
        private SyncToken token;
        private SyncResultsHandler syncHandler;
        private Configuration configuration;
        private GuardedString password;
        private ScriptContext scriptContext;
        private String username;

        ConnectorRequestBuilder() {
        }

        public ConnectorRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ConnectorRequestBuilder token(SyncToken token) {
            this.token = token;
            return this;
        }

        public ConnectorRequestBuilder password(GuardedString password) {
            this.password = password;
            return this;
        }

        public ConnectorRequestBuilder scriptContext(ScriptContext scriptContext) {
            this.scriptContext = scriptContext;
            return this;
        }

        public ConnectorRequestBuilder syncHandler(SyncResultsHandler syncHandler) {
            this.syncHandler = syncHandler;
            return this;
        }

        public ConnectorRequestBuilder objectClass(ObjectClass objectClass) {
            this.objectClass = objectClass;
            return this;
        }

        public ConnectorRequestBuilder uid(Uid uid) {
            this.uid = uid;
            return this;
        }

        public ConnectorRequestBuilder options(OperationOptions options) {
            this.options = options;
            return this;
        }

        public ConnectorRequestBuilder filter(Filter query) {
            this.query = query;
            return this;
        }

        public ConnectorRequestBuilder resultsHandler(ResultsHandler resultsHandler) {
            this.resultsHandler = resultsHandler;
            return this;
        }

        public ConnectorRequestBuilder modifications(Collection<? extends AttributeDelta> modifications) {
            if (this.modifications == null)
                this.modifications = new HashSet<>();
            this.modifications.addAll(modifications);
            return this;
        }

        public ConnectorRequestBuilder createAttributes(Collection<? extends Attribute> createAttributes) {
            if (this.createAttributes == null)
                this.createAttributes = new HashSet<>();
            this.createAttributes.addAll(createAttributes);
            return this;
        }

        public ConnectorRequestBuilder operationType(OperationType operationType) {
            this.operationType = operationType;
            return this;
        }

        public ConnectorRequestBuilder configuration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public ConnectorRequest build() {
            return new ConnectorRequest(objectClass, uid, options, query, resultsHandler,
                    modifications, createAttributes, operationType, token, syncHandler,
                    configuration, password, scriptContext, username);
        }
    }
}
