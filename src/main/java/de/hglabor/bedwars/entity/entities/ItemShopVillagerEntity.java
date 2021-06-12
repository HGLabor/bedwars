package de.hglabor.bedwars.entity.entities;

import de.hglabor.bedwars.config.localization.Locale;
import de.hglabor.bedwars.config.localization.Localization;
import de.hglabor.bedwars.entity.GameEntity;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemShopVillagerEntity extends GameEntity<Entity> {

    private static List<Class<? extends Entity>> list = new ArrayList<>();

    static {
        for (EntityType entityType : EntityType.values()) {
            Class<?> entityClass = entityType.getEntityClass();
            if(entityClass != null) {
                if(entityType.isSpawnable() && entityType.isAlive()) {
                    list.add((Class<? extends Entity>) entityClass);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public ItemShopVillagerEntity(Location location) {
        super(Localization.getMessage("map.entities.itemshop.name", Locale.ENGLISH), (Class<Entity>) list.get(new Random().nextInt(list.size()-1)), location);
    }

    @Override
    public void onClick(Entity context, Player player) {
        //TODO open item shop
    }
}
