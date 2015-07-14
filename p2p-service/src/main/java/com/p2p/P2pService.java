package com.p2p;

import com.p2p.config.*;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Optional;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class P2pService extends Application<P2pConfiguration> {
    private static final Logger LOG = LoggerFactory.getLogger(P2pService.class);

    public static void main(String[] args) throws Exception {
        new P2pService().run(args);
    }

    private final HibernateBundle<P2pConfiguration> hibernateBundle = new HibernateBundle<P2pConfiguration>(
            
            Void.class
        ) {
        @Override
        public DataSourceFactory getDataSourceFactory(P2pConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public String getName() {
        return "p2p";
    }

    @Override
    public void initialize(Bootstrap<P2pConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/app/", "/", "index.html"));
        bootstrap.addBundle(hibernateBundle);
        ObjectMapper mapper = bootstrap.getObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void run(P2pConfiguration configuration,
                    Environment environment) throws Exception {
        environment.jersey().setUrlPattern("/p2p/*");
        environment.jersey().disable();
        
        
    }
}
