package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.VoxelSniper;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;

public enum Messages implements ComponentLike {
    BRUSH_MESSAGE_PREFIX,
    BRUSH_NAME,
    BRUSH_CENTER,
    BRUSH_HEIGHT,
    BRUSH_SIZE,
    BRUSH_SIZE_LARGE,
    VOXEL_MAT,
    VOXEL_DATA,
    VOXEL_LIST,
    VOXEL_LIST_EMPTY,
    REPLACEMENT_MAT,
    REPLACEMENT_DATA,
    PERFORMER,
    TOGGLE_LIGHTNING,
    TOGGLE_PRINTOUT,
    TOGGLE_RANGE,
    PERFORMER_MESSAGE,
    BLOCKS_UNTIL_REPEATER_MESSAGE,
    BLOCK_POWER_MESSAGE,
    FACE_POWER_MESSAGE,
    TREESNIPE_USAGE,
    TREESNIPE_BRUSH_MESSAGE,
    VOLTMETER_BRUSH_MESSAGE,
    DEFAULT_STAMP,
    NO_AIR_STAMP,
    FILL_STAMP,
    CLONE_STAMP_ERROR,
    OFF_WORLD_END_POS,
    OFF_WORLD_START_POS,
    ENTITY_BRUSH_MESSAGE,
    LIGHTNING_BRUSH_MESSAGE,
    SELECTION_SIZE_ABOVE_LIMIT,
    POINT_1_SET,
    POINT_2_SET,
    REGENERATE_CHUNK_MESSAGE,
    ROT3D_BRUSH_MESSAGE,
    ;

    // TODO move this into YAML (probably with some kind of tool)
    private @NotNull String message = this.name().toLowerCase(Locale.ROOT);

    private static YamlConfiguration getMessageFile(ClassLoader loader, String fileName, VoxelSniper voxelSniper) {
        YamlConfiguration yaml = new YamlConfiguration();

        try(InputStream inputstream = loader.getResourceAsStream(fileName)) {
            if(inputstream != null) {
                try(InputStreamReader reader = new InputStreamReader(inputstream)) {
                    try {
                        yaml.load(reader);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } else {
                voxelSniper.getLogger().warning("The resource " + fileName + " could not be found!");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return yaml;
    }

    public static void load(VoxelSniper voxelSniper) {
        File langFile = new File(voxelSniper.getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            voxelSniper.saveResource("lang.yml", false);
        }
        YamlConfiguration lang = YamlConfiguration.loadConfiguration(langFile);
        lang.options().width(500);
        YamlConfiguration fallBackLang = getMessageFile(Messages.class.getClassLoader(), "lang.yml", voxelSniper);
        boolean changedFile = false;

        for (Messages message : values()) {
            String langMessage = lang.getString(message.name());
            if (langMessage == null) {
                // try to read from internal file
                langMessage = fallBackLang.getString(message.name());
                if (langMessage == null) {
                    voxelSniper.getLogger().warning("Unable to load message " + message.name() + ". This is an error and should be reported.");
                } else {
                    // add default to 'public' file
                    lang.set(message.name(), langMessage);
                    changedFile = true;
                    message.message = langMessage;
                }
            } else {
                message.message = langMessage;
            }
        }

        if (changedFile) {
            try {
                lang.save(langFile);
            } catch (IOException ignored) {
            }
        }
    }

    @NotNull public final Message replace(String pattern, Object replacement) {
        return new Message(this.message).replace(pattern, replacement);
    }

    @NotNull public final Message append(String message) {
        return new Message(this.message).append(message);
    }

    public ComponentLike append(ComponentLike message) {
        return this.asComponent().append(message);
    }

    @Override
    public @NotNull Component asComponent() {
        return MiniMessage.miniMessage().deserialize(this.message);
    }

    public static final class Message implements ComponentLike {
        @NotNull private String message;

        private Message(@NotNull String message) {
            this.message = message;
        }

        @NotNull public Message replace(String target, Object replacement) {
            if (target != null && replacement != null)
                this.message = this.message.replace(target, String.valueOf(replacement));

            return this;
        }

        @NotNull public Message append(String message) {
            if (message != null) this.message += message;
            return this;
        }

        @Override
        public @NotNull Component asComponent() {
            return MiniMessage.miniMessage().deserialize(this.message);
        }
    }
}