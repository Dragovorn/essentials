package com.dragovorn.dragonbot.essentials;

import com.dragovorn.dragonbot.api.bot.configuration.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EssentialsConfiguration extends Configuration {

    EssentialsConfiguration(File file) {
        super(file);
    }

    private Map<String, Object> defaults = new HashMap<>();

    private void addDefaults() {
        this.defaults.clear();

        this.defaults.put("discord", "");
    }

    private void setDefaults() {
        addDefaults();

        this.entries.clear();
        this.entries.putAll(this.defaults);
    }

    public void update() {
        addDefaults();

        this.defaults.entrySet().forEach(entry -> {
            if (this.entries.containsKey(entry.getKey())) {
                this.entries.put(entry.getKey(), entry.getValue());
            }
        });
    }

    public void generate() {
        setDefaults();
        save();
    }

    public String getDiscord() {
        return getString("discord");
    }
}