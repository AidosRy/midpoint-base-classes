package base;


import common.*;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.exceptions.InvalidAttributeValueException;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.PoolableConnector;
import org.identityconnectors.framework.spi.operations.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static org.identityconnectors.common.CollectionUtil.isEmpty;

public abstract class BaseConnector extends Logger implements PoolableConnector,
        ILogger,
        AuthenticateOp,
        ResolveUsernameOp,
        ScriptOnConnectorOp,
        ScriptOnResourceOp,
        CreateOp,
        UpdateDeltaOp,
        DeleteOp,
        SchemaOp,
        TestOp,
        SearchOp<BaseFilter>,
        SyncOp
{
    public static Configuration configuration;

    public abstract BaseHandler createObjectHandler(ObjectClass objectClass);

    public abstract Map<OperationType, Set<String>> initObjectClassValuesByOperationType();

    @Override
    public Configuration getConfiguration()
    {
        return configuration;
    }

    public final Map<OperationType, Set<String>> objectClassValues = initObjectClassValuesByOperationType();

    @Override
    public Schema schema() {
        var request = builder()
                .operationType(OperationType.SCHEMA)
                .build();
        return executeRequest(request, false).getSchema();
    }

    @Override
    public void init(Configuration config) {
        var request = builder()
                .operationType(OperationType.INIT)
                .build();
        executeRequest(request, false);
    }

    @Override
    public void dispose() {
        var request = builder()
                .operationType(OperationType.DISPOSE)
                .build();
        executeRequest(request, false);
    }

    @Override
    public void checkAlive() {
        var request = builder()
                .operationType(OperationType.CHECK)
                .build();
        executeRequest(request, false);
    }

    @Override
    public void test() {
        var request = builder()
                .operationType(OperationType.TEST)
                .build();
        executeRequest(request, false);
    }

    @Override
    public Uid create(ObjectClass objectClass, Set<Attribute> createAttributes, OperationOptions options) {
        var request = builder()
                .operationType(OperationType.CREATE)
                .objectClass(objectClass)
                .createAttributes(createAttributes)
                .options(options)
                .build();
        return executeRequest(request, true).getUid();
    }

    @Override
    public Set<AttributeDelta> updateDelta(ObjectClass objectClass, Uid uid, Set<AttributeDelta> modifications, OperationOptions options) {
        var request = builder()
                .operationType(OperationType.UPDATE)
                .objectClass(objectClass)
                .uid(uid)
                .options(options)
                .modifications(modifications)
                .build();
        return executeRequest(request, true).getModifications();
    }

    @Override
    public void delete(ObjectClass objectClass, Uid uid, OperationOptions options) {
        var request = builder()
                .operationType(OperationType.DELETE)
                .objectClass(objectClass)
                .uid(uid)
                .options(options)
                .build();
        executeRequest(request,true);
    }

    @Override
    public void executeQuery(ObjectClass objectClass, BaseFilter filter, ResultsHandler resultsHandler, OperationOptions options) {
        var request = builder()
                .operationType(OperationType.QUERY)
                .objectClass(objectClass)
                .filter(filter)
                .resultsHandler(resultsHandler)
                .options(options)
                .build();
        executeRequest(request,true);
    }

    @Override
    public void sync(ObjectClass objectClass, SyncToken token, SyncResultsHandler syncHandler, OperationOptions options) {
        var request = builder()
                .operationType(OperationType.SYNC)
                .objectClass(objectClass)
                .token(token)
                .syncHandler(syncHandler)
                .options(options)
                .build();
        executeRequest(request, false);
    }

    @Override
    public SyncToken getLatestSyncToken(ObjectClass objectClass) {
        var request = builder()
                .operationType(OperationType.TOKEN)
                .objectClass(objectClass)
                .build();
        return executeRequest(request, true).getToken();
    }

    @Override
    public Uid authenticate(ObjectClass objectClass, String userName, GuardedString password, OperationOptions options) {
        var request = builder()
                .operationType(OperationType.AUTH)
                .objectClass(objectClass)
                .username(userName)
                .password(password)
                .options(options)
                .build();
        return executeRequest(request, true).getUid();
    }

    @Override
    public Uid resolveUsername(ObjectClass objectClass, String userName, OperationOptions options) {
        var request = builder()
                .operationType(OperationType.RESOLVE_USERNAME)
                .objectClass(objectClass)
                .username(userName)
                .options(options)
                .build();
        return executeRequest(request, true).getUid();
    }

    @Override
    public Object runScriptOnConnector(ScriptContext scriptContext, OperationOptions options) {
        var request = builder()
                .operationType(OperationType.SCRIPT_CONNECTOR)
                .scriptContext(scriptContext)
                .options(options)
                .build();
        return executeRequest(request, false).getScriptResponse();
    }

    @Override
    public Object runScriptOnResource(ScriptContext scriptContext, OperationOptions options) {
        var request = builder()
                .operationType(OperationType.SCRIPT_RESOURCE)
                .scriptContext(scriptContext)
                .options(options)
                .build();
        return executeRequest(request, false).getScriptResponse();
    }

    ConnectorRequest.ConnectorRequestBuilder builder() {
        return ConnectorRequest.builder();
    }


    public ConnectorResponse sendRequest(ConnectorRequest request, BaseHandler handler) {

        ObjectClass objectClass = request.getObjectClass();
        OperationOptions options = request.getOptions();

        switch (request.getOperationType()) {

            case CREATE:
                if (isEmpty(request.getCreateAttributes()))
                    throw new InvalidAttributeValueException("Attributes not provided or empty");
                return new ConnectorResponse(handler.create(objectClass, request.getCreateAttributes(), options));

            case UPDATE:
                if(isEmpty(request.getModifications()))
                    throw new InvalidAttributeValueException("AttributeDeltas not provided or empty");
                return new ConnectorResponse(handler.updateDelta(objectClass, request.getUid(), request.getModifications(), options));

            case DELETE:
                handler.delete(objectClass, request.getUid(), options);
                break;

            case QUERY:
                handler.executeQuery(objectClass, (BaseFilter) request.getQuery(), request.getResultsHandler(), options);
                break;

            case SYNC:
                handler.sync(objectClass, request.getToken(), request.getSyncHandler(), options);
                break;

            case TOKEN:
                return new ConnectorResponse(handler.getLatestSyncToken(objectClass));

            case CREATE_FILTER:
                return new ConnectorResponse(handler.createFilterTranslator(objectClass, options));

            case TEST:
                handler.test();
                break;

            case CHECK:
                handler.checkAlive();
                break;

            case DISPOSE:
                handler.dispose();
                break;

            case INIT:
                configuration = request.getConfiguration();
                handler.init(configuration);
                break;

            case SCHEMA:
                return new ConnectorResponse(handler.schema());

            case AUTH:
                return new ConnectorResponse(handler.authenticate(objectClass, request.getUsername(), request.getPassword(), options));

            case SCRIPT_CONNECTOR:
                return new ConnectorResponse(handler.runScriptOnConnector(request.getScriptContext(), options));

            case RESOLVE_USERNAME:
                return new ConnectorResponse(handler.resolveUsername(objectClass, request.getUsername(), options));

            case SCRIPT_RESOURCE:
                return new ConnectorResponse(handler.runScriptOnResource(request.getScriptContext(), options));

            default:
                throw new RuntimeException("Incorrect operation type provided:" + request.getOperationType() + ". Possible operations: " + Arrays.toString(OperationType.values()));
        }
        return null;
    }

    public ConnectorResponse executeRequest(ConnectorRequest request, boolean requiresObjectClass) {

        OperationType operationType = request.getOperationType();
        String operationTypeStr = request.getOperationType().toString();
        info(operationTypeStr, request.getStartMessage());

        ObjectClass objectClass = request.getObjectClass();
        if (requiresObjectClass && isNull(objectClass))
            throw new InvalidAttributeValueException("ObjectClass value not provided");

        if (requiresObjectClass && !objectClassValues.get(operationType).contains(objectClass.getObjectClassValue()))
            throw new InvalidAttributeValueException(objectClass + " not implemented for " + operationTypeStr);

        BaseHandler handler = createObjectHandler(objectClass);

        ConnectorResponse response = sendRequest(request, handler);
        info(operationTypeStr, request.getSuccessMessage(response));

        return response;
    }
}
