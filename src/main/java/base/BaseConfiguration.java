package base;

import org.identityconnectors.framework.spi.AbstractConfiguration;
import org.identityconnectors.framework.spi.ConfigurationProperty;

public class BaseConfiguration extends AbstractConfiguration {

    private String url;

    @Override
    public void validate() {
    }

    @ConfigurationProperty
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
