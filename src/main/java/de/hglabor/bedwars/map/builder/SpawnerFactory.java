package de.hglabor.bedwars.map.builder;

import de.hglabor.bedwars.map.Spawner;
import org.bukkit.Location;
import org.bukkit.Material;

public class SpawnerFactory {

    private int tickDelayBetweenSpawns;
    private int spawnTickRate;
    private Material material;
    private Location location;
    private String itemName;

    public int getTickDelayBetweenSpawns() {
        return tickDelayBetweenSpawns;
    }

    public void setTickDelayBetweenSpawns(int tickDelayBetweenSpawns) {
        this.tickDelayBetweenSpawns = tickDelayBetweenSpawns;
    }

    public int getSpawnTickRate() {
        return spawnTickRate;
    }

    public void setSpawnTickRate(int spawnTickRate) {
        this.spawnTickRate = spawnTickRate;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Spawner constructOne() {
        return new Spawner(tickDelayBetweenSpawns, spawnTickRate, material, location, itemName);
    }
}
