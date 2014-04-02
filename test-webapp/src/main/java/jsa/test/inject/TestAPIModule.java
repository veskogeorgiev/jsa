package jsa.test.inject;

import java.util.List;

import jsa.endpoint.cxf.JaxRsConfig;
import jsa.inject.APIModule;
import jsa.inject.RedirectModule;
import jsa.test.impl.DemoAPIImpl;
import jsa.test.impl.ItemsAPIv1Impl;
import jsa.test.impl.ItemsAPIv2Impl;
import jsa.test.impl.ItemsAPIv3Impl;
import jsa.test.port.api.TestExcetionMapper;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class TestAPIModule extends APIModule {

    public TestAPIModule() {
        super("/api", "jsa.test.port.api");
    }

    @Override
    protected void configure() {
        super.configure();
        withJaxRsConfig(new TestJaxRsConfig());

        install(new RedirectModule()
                .with("/api/d", "/api/DemoAPI/v1/rest", true)
                .with("/api/ItemsAPI/v1/rest", "/api/ItemsAPI/v2/rest")
                .with("/api/ItemsAPI/v1/thrift", "/api/ItemsAPI/v2/thrift"));

        bind(jsa.test.api.v1.ItemsAPI.class).to(ItemsAPIv1Impl.class);
        bind(jsa.test.api.v2.ItemsAPI.class).to(ItemsAPIv2Impl.class);
        bind(jsa.test.api.v3.ItemsAPI.class).to(ItemsAPIv3Impl.class);

        bind(jsa.test.api.v1.DemoAPI.class).to(DemoAPIImpl.class);
    }

    static class TestJaxRsConfig implements JaxRsConfig {

        @Override
        public void addProviders(List<Object> providers) {
            providers.add(new TestExcetionMapper());
            providers.add(new JacksonJsonProvider());
        }
    }

}
