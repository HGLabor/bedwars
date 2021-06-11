package de.hglabor.bedwars.map;

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
}
