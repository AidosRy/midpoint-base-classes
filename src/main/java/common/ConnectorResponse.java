package common;

import org.identityconnectors.framework.common.objects.AttributeDelta;
import org.identityconnectors.framework.common.objects.Schema;
import org.identityconnectors.framework.common.objects.SyncToken;
import org.identityconnectors.framework.common.objects.Uid;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.identityconnectors.framework.common.objects.filter.FilterTranslator;

import java.util.Set;

public class ConnectorResponse {
    private FilterTranslator<Filter> filterTranslator;
    private SyncToken token;
    private Set<AttributeDelta> modifications;
    private Schema schema;
    private Uid uid;
    private Object scriptResponse;

    public ConnectorResponse(FilterTranslator<Filter> filterTranslator) {
        this.filterTranslator = filterTranslator;
    }

    public ConnectorResponse(Object scriptResponse) {
        this.scriptResponse = scriptResponse;
    }

    public ConnectorResponse(Uid uid) {
        this.uid = uid;
    }

    public ConnectorResponse(Schema schema) {
        this.schema = schema;
    }

    public ConnectorResponse(Set<AttributeDelta> modifications) {
        this.modifications = modifications;
    }

    public ConnectorResponse(SyncToken token) {
        this.token = token;
    }

    public Object getScriptResponse() {
        return scriptResponse;
    }

    public FilterTranslator<Filter> getFilterTranslator() {
        return filterTranslator;
    }

    public void setFilterTranslator(FilterTranslator<Filter> filterTranslator) {
        this.filterTranslator = filterTranslator;
    }

    public SyncToken getToken() {
        return token;
    }

    public void setToken(SyncToken token) {
        this.token = token;
    }

    public Set<AttributeDelta> getModifications() {
        return modifications;
    }

    public void setModifications(Set<AttributeDelta> modifications) {
        this.modifications = modifications;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Uid getUid() {
        return uid;
    }

    public void setUid(Uid uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "ConnectorResponse{" +
                "filterTranslator=" + filterTranslator +
                ", token=" + token +
                ", modifications=" + modifications +
                ", schema=" + schema +
                ", uid=" + uid +
                ", scriptResponse=" + scriptResponse +
                '}';
    }
}
