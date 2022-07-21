package com.thevoxelbox.voxelsniper.voxelsniper.chunk;

import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import org.bukkit.entity.Entity;

public interface IChunk {
    int getX();
    int getZ();

    IBlock getBlock(int x, int y, int z);

    Iterable<? extends IEntity> getEntities();
}
