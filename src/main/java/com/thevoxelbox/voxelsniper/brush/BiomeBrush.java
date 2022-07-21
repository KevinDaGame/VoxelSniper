package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.voxelsniper.biome.VoxelBiome;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class BiomeBrush extends Brush {

    private VoxelBiome selectedBiome = VoxelBiome.PLAINS;

    public BiomeBrush() {
        this.setName("Biome");
    }

    private void biome(final SnipeData v) {
        final int brushSize = v.getBrushSize();
        final double brushSizeSquared = Math.pow(brushSize, 2);

        for (int x = -brushSize; x <= brushSize; x++) {
            final double xSquared = Math.pow(x, 2);

            for (int z = -brushSize; z <= brushSize; z++) {
                if ((xSquared + Math.pow(z, 2)) <= brushSizeSquared) {
                    this.getWorld().setBiome(this.getTargetBlock().getX() + x, this.getTargetBlock().getZ() + z, this.selectedBiome);
                }
            }
        }

        final IBlock block1 = this.getWorld().getBlock(this.getTargetBlock().getX() - brushSize, 0, this.getTargetBlock().getZ() - brushSize);
        final IBlock block2 = this.getWorld().getBlock(this.getTargetBlock().getX() + brushSize, 0, this.getTargetBlock().getZ() + brushSize);

        final int lowChunkX = (block1.getX() <= block2.getX()) ? block1.getChunk().getX() : block2.getChunk().getX();
        final int lowChunkZ = (block1.getZ() <= block2.getZ()) ? block1.getChunk().getZ() : block2.getChunk().getZ();
        final int highChunkX = (block1.getX() >= block2.getX()) ? block1.getChunk().getX() : block2.getChunk().getX();
        final int highChunkZ = (block1.getZ() >= block2.getZ()) ? block1.getChunk().getZ() : block2.getChunk().getZ();

        for (int x = lowChunkX; x <= highChunkX; x++) {
            for (int z = lowChunkZ; z <= highChunkZ; z++) {
                this.getWorld().refreshChunk(x, z);
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.biome(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.biome(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.custom(ChatColor.GOLD + "Currently selected biome type: " + ChatColor.DARK_GREEN + this.selectedBiome.key());
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Biome Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " [biomeType] -- Change brush to the specified biome");
            return;
        }

        try {
            this.selectedBiome = VoxelBiome.getBiome(params[0].toUpperCase());
            v.sendMessage(ChatColor.GOLD + "Currently selected biome type: " + ChatColor.DARK_GREEN + this.selectedBiome.key());
        } catch (IllegalArgumentException e) {
            v.sendMessage(ChatColor.RED + "That biome does not exist.");
        }
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Arrays.stream(Biome.values()).map(e -> e.name()).collect(Collectors.toList()));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.biome";
    }
}
