package de.hglabor.bedwars.map;

import de.hglabor.bedwars.Bedwars;
import de.hglabor.utils.noriskutils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public class Spawner {

    private final int tickDelayBetweenSpawns;
    private final int spawnTickRate;
    private final Material material;
    private final Location location;
    private final String itemName;
    private BukkitTask bukkitTask;

    private int tick = 0;

    public Spawner(int tickDelayBetweenSpawns, int spawnTickRate, Material material, Location location, String itemName) {
        this.tickDelayBetweenSpawns = tickDelayBetweenSpawns;
        this.spawnTickRate = spawnTickRate;
        this.material = material;
        this.location = location;
        this.itemName = itemName;
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                tick++;
                if(tick == spawnTickRate) {
                    if(location.getWorld() != null) {
                        location.getWorld().dropItemNaturally(location.clone().add(0, 2, 0), new ItemBuilder(material).setName(itemName).build());
                    }
                    tick = 0;
                }
            }
        }.runTaskTimer(Bedwars.getPlugin(), 0, tickDelayBetweenSpawns);

    }

    public int getTickDelayBetweenSpawns() {
        return tickDelayBetweenSpawns;
    }

    public int getSpawnTickRate() {
        return spawnTickRate;
    }

    public Material getMaterial() {
        return material;
    }

    public void endTasks() {
        bukkitTask.cancel();
    }

    public static Spawner createByFile(File file) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return new Spawner(
                yamlConfiguration.getInt("spawner.tickDelayBetweenSpawns"),
                yamlConfiguration.getInt("spawner.spawnTickRate"),
                Material.valueOf(yamlConfiguration.getString("spawner.material").toUpperCase()),
                yamlConfiguration.getLocation("spawner.location"),
                yamlConfiguration.getString("spawner.itemName")
        );
    }
}
