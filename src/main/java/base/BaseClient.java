package base;

import common.Logger;
import org.identityconnectors.framework.spi.AbstractConfiguration;

public abstract class BaseClient extends Logger {

    public AbstractConfiguration configuration;

    public Object connection;//TODO change to pool

    public String serverUrl;

    public abstract void openConnection(String url);

    public abstract void dispose();

}
