package com.gulrajani.bracket.server;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BracketApplication extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new BracketApplication().run(args);
    }

    @Override
    public String getName() {
        return "BracketApplication";
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new AssetsBundle("/assets/", "/foo"));
    }

    @Override
    public void run(Configuration arg0, Environment environment) throws Exception {
        environment.jersey().register(new BracketAdvanceResource());
    }

}
