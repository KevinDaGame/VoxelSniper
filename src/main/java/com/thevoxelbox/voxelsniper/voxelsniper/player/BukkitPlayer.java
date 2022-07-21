package com.thevoxelbox.voxelsniper.voxelsniper.player;

import com.thevoxelbox.voxelsniper.voxelsniper.block.BukkitBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.block.IBlock;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.BukkitEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.entity.IEntity;
import com.thevoxelbox.voxelsniper.voxelsniper.entitytype.IEntityType;
import com.thevoxelbox.voxelsniper.voxelsniper.location.BukkitLocation;
import com.thevoxelbox.voxelsniper.voxelsniper.location.ILocation;
import com.thevoxelbox.voxelsniper.voxelsniper.material.BukkitMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.material.MaterialFactory;
import com.thevoxelbox.voxelsniper.voxelsniper.material.VoxelMaterial;
import com.thevoxelbox.voxelsniper.voxelsniper.vector.IVector;
import com.thevoxelbox.voxelsniper.voxelsniper.world.BukkitWorld;
import org.bukkit.Material;
import org.bukkit.entity.*;

import java.util.*;

public class BukkitPlayer extends AbstractPlayer {
    private final Player player;

    public BukkitPlayer(Player player) {
        this.player = player;
    }

    @Override
    public BukkitWorld getWorld() {
        return new BukkitWorld(player.getWorld());
    }

    @Override
    public void addPassenger(IEntity entity) {

    }

    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public BukkitLocation getLocation() {
        return new BukkitLocation(player.getLocation());
    }

    @Override
    public void setVelocity(IVector velocity) {

    }

    @Override
    public boolean hasPermission(String permissionNode) {
        return player.hasPermission(permissionNode);
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public ILocation getEyeLocation() {
        return new BukkitLocation(player.getEyeLocation());
    }

    @Override
    public List<IEntity> getNearbyEntities(int x, int y, int z) {
        var entities = player.getNearbyEntities(x, y, z);
        var result = new ArrayList<IEntity>();
        for (var entity : entities) {
             result.add(new BukkitEntity(entity));
        }
        return result;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void teleport(ILocation location) {
        player.teleport(((BukkitLocation) location).getLocation());
    }

    @Override
    public IEntityType getType() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public int getEntityId() {
        return 0;
    }

    @Override
    public void eject() {
        player.eject();
    }

    @Override
    public IEntity launchProjectile(Class<? extends Projectile> fireball) {
        return new BukkitEntity(player.launchProjectile(fireball));
    }

    @Override
    public IBlock getTargetBlock(Set<VoxelMaterial> transparent, int maxDistance) {
        Set<Material> materials = new HashSet<>(transparent.size());
        for (VoxelMaterial material : transparent) {
            materials.add(((BukkitMaterial)MaterialFactory.getMaterial(material)).getMaterial());
        }
        return new BukkitBlock(player.getTargetBlock(materials, maxDistance));
    }

    @Override
    public VoxelMaterial getItemInHand() {
        var item = player.getInventory().getItemInMainHand();
        return new VoxelMaterial(item.getType().getKey().getKey());
    }

    public Player getPlayer() {
        return player;
    }
}
