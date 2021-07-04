package de.hglabor.bedwars.map;

import de.hglabor.bedwars.Bedwars;
import de.hglabor.bedwars.entity.GameEntity;
import de.hglabor.bedwars.entity.entities.ItemShopVillagerEntity;
import de.hglabor.bedwars.entity.entities.UpgradeShopVillagerEntity;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Map {

    private List<Base> bases;
    private List<Team> teams;
    private int size;
    private List<Spawner> spawners;
    private List<GameEntity<?>> entities;
    private String name;
    private int teamSize;

    public Map(String name, int size, int teamSize, List<Base> bases, List<Team> teams, List<Spawner> spawners, List<GameEntity<?>> entities) {
        this.name = name;
        this.size = size;
        this.teamSize = teamSize;
        this.bases = bases;
        this.teams = teams;
        this.spawners = spawners;
        this.entities = entities;
    }

    public void destroy() {
        for(GameEntity<?> gameEntity : getEntities()) {
            gameEntity.kill();
        }
        for (Spawner spawner : getSpawners()) {
            spawner.endTasks();
        }
    }

    public void addBase(Base base) {
        this.bases.add(base);
    }

    public void removeBase(Base base) {
        this.bases.remove(base);
    }

    public void addSpawner(Spawner spawner) {
        spawners.add(spawner);
    }

    public void removeSpawner(Spawner spawner) {
        spawners.remove(spawner);
    }

    public void addEntity(GameEntity<?> entity) {
        this.entities.add(entity);
    }

    public void removeEntity(GameEntity<?> entity) {
        this.entities.remove(entity);
        entity.kill();
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    public List<Base> getBases() {
        return bases;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public int getSize() {
        return size;
    }

    public List<Spawner> getSpawners() {
        return spawners;
    }

    public List<GameEntity<?>> getEntities() {
        return entities;
    }

    public String getName() {
        return name;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public static Map createByFile(File file) {
        try {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            String name = yamlConfiguration.getString("map.name");
            int size = yamlConfiguration.getInt("map.size");
            int teamSize = yamlConfiguration.getInt("map.teamSize");
            List<Spawner> spawners = new ArrayList<>();
            List<Base> bases = new ArrayList<>();
            List<Team> teams = new ArrayList<>();
            for (File teamFile : new File(Bedwars.getPlugin().getDataFolder(), "maps/" + name.toLowerCase() + "/teams/").listFiles()) {
                teams.add(Team.createByFile(teamFile));
            }
            List<GameEntity<?>> entities = new ArrayList<>();
            for (Object locationObj : yamlConfiguration.getList("map.entities.itemshops")) {
                entities.add(new ItemShopVillagerEntity((Location) locationObj));
            }
            for (Object locationObj : yamlConfiguration.getList("map.entities.upgradeshops")) {
                entities.add(new UpgradeShopVillagerEntity((Location) locationObj));
            }
            for (File baseFile : new File(Bedwars.getPlugin().getDataFolder(), "maps/" + name.toLowerCase() + "/bases/").listFiles()) {
                bases.add(Base.createByFile(baseFile, name));
            }
            for (File spawnerFile : new File(Bedwars.getPlugin().getDataFolder(), "maps/" + name.toLowerCase() + "/spawners/").listFiles()) {
                spawners.add(Spawner.createByFile(spawnerFile));
            }
            return new Map(
                    name, size, teamSize, bases, teams, spawners, entities
            );
        } catch (Exception exception) {
            System.out.println("Could not load map by file " + file + ": " + exception.getMessage());
            return new Map("Error", 8, 1, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        }
    }

}
