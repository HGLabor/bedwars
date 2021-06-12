package de.hglabor.bedwars.entity;

import de.hglabor.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.io.File;

public abstract class GameEntity<T extends Entity> {

    private final String name;
    private final Class<T> entityClazz;
    private final Location location;
    private Entity bukkitEntity;

    @SuppressWarnings("unchecked")
    public GameEntity(String name, Class<T> entityClazz, Location location) {
        this.name = name;
        this.entityClazz = entityClazz;
        this.location = location;
        if(location.getWorld() != null) {
            Entity bukkitEntity = location.getWorld().spawn(this.location, this.entityClazz);
            bukkitEntity.setCustomNameVisible(true);
            bukkitEntity.setCustomName(name);
            bukkitEntity.setSilent(true);
            bukkitEntity.setInvulnerable(true);
            if(bukkitEntity instanceof Mob) {
                ((Mob) bukkitEntity).setAI(false);
            }
            Bukkit.getPluginManager().registerEvents(new Listener() {
                @EventHandler
                public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
                    if(event.getRightClicked() == bukkitEntity) {
                        onClick((T) event.getRightClicked(), event.getPlayer());
                    }
                }

                @EventHandler
                public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
                    if(event.getRightClicked() == bukkitEntity) {
                        onClick((T) event.getRightClicked(), event.getPlayer());
                    }
                }
            }, Bedwars.getPlugin());
            onSpawn((T) bukkitEntity);
        }
    }

    public void kill() {
        if(bukkitEntity != null) {
            bukkitEntity.remove();
        }
    }

    public void onSpawn(T context) {

    }

    public abstract void onClick(T context, Player player);


}
