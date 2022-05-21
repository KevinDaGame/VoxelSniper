package com.thevoxelbox.voxelsniper;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Configuration storage defining global configurations for VoxelSniper.
 */
public class VoxelSniperConfiguration {

    public static final String CONFIG_IDENTIFIER_UNDO_CACHE_SIZE = "undo-cache-size";
    public static final String CONFIG_IDENTIFIER_MESSAGE_ON_LOGIN_ENABLED = "message-on-login-enabled";
    public static final int DEFAULT_UNDO_CACHE_SIZE = 20;
    public static final boolean DEFAULT_MESSAGE_ON_LOGIN_ENABLED = true;
    private final FileConfiguration configuration;

    /**
     * @param configuration Configuration that is going to be used.
     */
    public VoxelSniperConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Returns the maximum amount of snipes stored in the undo cache of snipers.
     *
     * @return the maximum amount of snipes stored in the undo cache of snipers
     */
    public int getUndoCacheSize() {
        return configuration.getInt(CONFIG_IDENTIFIER_UNDO_CACHE_SIZE, DEFAULT_UNDO_CACHE_SIZE);
    }

    /**
     * Set the maximum amount of snipes stored in the undo cache of snipers.
     *
     * @param size size of undo cache
     */
    public void setUndoCacheSize(int size) {
        configuration.set(CONFIG_IDENTIFIER_UNDO_CACHE_SIZE, size);
    }

    /**
     * Returns if the login message is enabled.
     *
     * @return true if message on login is enabled, false otherwise.
     */
    public boolean isMessageOnLoginEnabled() {
        return configuration.getBoolean(CONFIG_IDENTIFIER_MESSAGE_ON_LOGIN_ENABLED, DEFAULT_MESSAGE_ON_LOGIN_ENABLED);
    }

    /**
     * Set the message on login to be enabled or disabled.
     *
     * @param enabled Message on Login enabled
     */
    public void setMessageOnLoginEnabled(boolean enabled) {
        configuration.set(CONFIG_IDENTIFIER_MESSAGE_ON_LOGIN_ENABLED, enabled);
    }
}
