package com.dragovorn.dragonbot.essentials;

import com.dragovorn.dragonbot.api.bot.configuration.Configuration;

import java.io.File;

/**
 * @author Dragovorn
 * @since 1.00a
 *
 * Custom configuration class that contains getters for all of the essentials configuration values
 */
public class EssentialsConfiguration extends Configuration {

    EssentialsConfiguration(File file) {
        super(file);
    }

    @Override
    protected void addDefaults() {
        this.defaults.clear();

        this.defaults.put("discord", "");
    }

    public String getDiscord() {
        return getString("discord");
    }
}