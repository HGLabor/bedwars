package de.hglabor.bedwars.map.builder;

import de.hglabor.bedwars.entity.entities.ItemShopVillagerEntity;
import de.hglabor.bedwars.entity.entities.UpgradeShopVillagerEntity;

import java.util.ArrayList;
import java.util.List;

public class MapFactory {

    private String name;
    private int size;
    private int teamSize;
    private List<BaseFactory> bases = new ArrayList<>();
    private List<TeamFactory> teams = new ArrayList<>();
    private List<SpawnerFactory> spawner = new ArrayList<>();
    private List<ItemShopVillagerEntity> itemShops = new ArrayList<>();
    private List<UpgradeShopVillagerEntity> upgradeShops = new ArrayList<>();

    public List<BaseFactory> getBases() {
        return bases;
    }

    public List<TeamFactory> getTeams() {
        return teams;
    }

    public List<SpawnerFactory> getSpawner() {
        return spawner;
    }

    public List<ItemShopVillagerEntity> getItemShops() {
        return itemShops;
    }

    public List<UpgradeShopVillagerEntity> getUpgradeShops() {
        return upgradeShops;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public void addBase(BaseFactory baseFactory) {
        bases.add(baseFactory);
    }

    public void removeBase(BaseFactory baseFactory) {
        bases.remove(baseFactory);
    }

    public void addTeam(TeamFactory teamFactory) {
        teams.add(teamFactory);
    }

    public void removeTeam(TeamFactory teamFactory) {
        teams.remove(teamFactory);
    }

    public void addSpawner(SpawnerFactory spawnerFactory) {
        spawner.add(spawnerFactory);
    }

    public void removeSpawner(SpawnerFactory spawnerFactory) {
        spawner.remove(spawnerFactory);
    }

    public void addUpgradeShop(UpgradeShopVillagerEntity upgradeShopVillagerEntity) {
        this.upgradeShops.add(upgradeShopVillagerEntity);
    }

    public void removeUpgradeShop(UpgradeShopVillagerEntity upgradeShopVillagerEntity) {
        this.upgradeShops.remove(upgradeShopVillagerEntity);
    }

    public void addItemShop(ItemShopVillagerEntity itemShopVillagerEntity) {
        this.itemShops.add(itemShopVillagerEntity);
    }

    public void removeItemShop(ItemShopVillagerEntity itemShopVillagerEntity) {
        this.itemShops.remove(itemShopVillagerEntity);
    }
}
