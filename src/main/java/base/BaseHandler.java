package base;

import common.Logger;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.objects.*;
import org.identityconnectors.framework.spi.Configuration;

import java.util.Set;


public abstract class BaseHandler extends Logger {

    public abstract void dispose();

    public abstract Uid create(ObjectClass objectClass, Set<Attribute> createAttributes, OperationOptions options);

    public abstract Set<AttributeDelta> updateDelta(ObjectClass objectClass, Uid uid, Set<AttributeDelta> modifications, OperationOptions options);

    public abstract void delete(ObjectClass objectClass, Uid uid, OperationOptions options);

    public abstract void executeQuery(ObjectClass objectClass, BaseFilter query, ResultsHandler resultsHandler, OperationOptions options);

    public abstract Uid authenticate(ObjectClass objectClass, String username, GuardedString password, OperationOptions options);

    public abstract Schema schema();

    public abstract void init(Configuration configuration);

    public abstract void test();

    public abstract void checkAlive();

    public abstract void sync(ObjectClass objectClass, SyncToken token, SyncResultsHandler syncHandler, OperationOptions options);

    public abstract SyncToken getLatestSyncToken(ObjectClass objectClass);

    public abstract Set<AttributeDelta> createFilterTranslator(ObjectClass objectClass, OperationOptions options);

    public abstract Set<AttributeDelta> runScriptOnConnector(ScriptContext scriptContext, OperationOptions options);

    public abstract Set<AttributeDelta> resolveUsername(ObjectClass objectClass, String username, OperationOptions options);

    public abstract Set<AttributeDelta> runScriptOnResource(ScriptContext scriptContext, OperationOptions options);
}