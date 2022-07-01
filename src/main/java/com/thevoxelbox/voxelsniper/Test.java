package com.thevoxelbox.voxelsniper;

import org.jnbt.CompoundTag;
import org.jnbt.NBTInputStream;
import org.jnbt.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        try {
            File f = new File("/home/kevin/IdeaProjects/VoxelSniper/src/main/java/com/thevoxelbox/voxelsniper/lamp.schem");
            FileInputStream fis = new FileInputStream(f);
            NBTInputStream nbt = new NBTInputStream(fis);
            CompoundTag backuptag = (CompoundTag) nbt.readTag();
            Map<String, Tag> tagCollection = backuptag.getValue();

            short width = (Short) getChildTag(tagCollection, "Width").getValue();
            short height = (Short) getChildTag(tagCollection, "Height").getValue();
            short length = (Short) getChildTag(tagCollection, "Length").getValue();

            byte[] blocks = (byte[]) getChildTag(tagCollection, "Blocks").getValue();
            byte[] data = (byte[]) getChildTag(tagCollection, "Data").getValue();

            List entities = (List) getChildTag(tagCollection, "Entities").getValue();
            List tileentities = (List) getChildTag(tagCollection, "TileEntities").getValue();
            nbt.close();
            fis.close();
            System.out.println(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static Tag getChildTag(Map<String, Tag> items, String key) {
        return items.get(key);
    }

}
