package com.chill.talkies.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpringApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String IN_MEMORY_PROFILE = "in-memory";
    public static final String REMOTE_PROFILE = "remote";
    private static final Log logger = LogFactory.getLog(SpringApplicationContextInitializer.class);
    private static final List<String> validLocalProfiles = Arrays.asList("postgres");

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment appEnvironment = applicationContext.getEnvironment();

        String[] persistenceProfiles = getActiveProfile(appEnvironment);

        if (persistenceProfiles == null) {
            persistenceProfiles = new String[]{IN_MEMORY_PROFILE};
        }

        for (String persistenceProfile : persistenceProfiles) {
            appEnvironment.addActiveProfile(persistenceProfile);
        }
    }

    private String[] getActiveProfile(ConfigurableEnvironment appEnvironment) {
        List<String> serviceProfiles = new ArrayList<>();

        logger.debug("appEnv.getActiveProfiles: " + String.join(",", appEnvironment.getActiveProfiles()));

        boolean isRemote = Arrays.asList(appEnvironment.getActiveProfiles()).contains(REMOTE_PROFILE);
        logger.debug("isRemote datasource ? " + isRemote);

        for (String profile : appEnvironment.getActiveProfiles()) {
            if (validLocalProfiles.contains(profile)) {
                serviceProfiles.add(profile);
                logger.debug("Added profile: " + profile);
            }
        }

        if (serviceProfiles.size() > 1) {
            throw new IllegalStateException("Only one active Spring profile may be set among the following: " +
                    validLocalProfiles.toString() + ". " +
                    "These profiles are active: [" +
                    StringUtils.collectionToCommaDelimitedString(serviceProfiles) + "]");
        }

        if (serviceProfiles.size() > 0) {
            if (isRemote) {
                logger.debug("creating remote profiles");
                return createProfileNames(serviceProfiles.get(0), "remote");
            } else {
                logger.debug("creating local profiles");
                return createProfileNames(serviceProfiles.get(0), "local");
            }
        }

        return null;
    }

    private String[] createProfileNames(String baseName, String suffix) {
        String[] profileNames = {baseName, baseName + "-" + suffix};
        logger.info("Setting profile names: " + StringUtils.arrayToCommaDelimitedString(profileNames));
        return profileNames;
    }
}
