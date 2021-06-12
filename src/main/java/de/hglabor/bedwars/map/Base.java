package de.hglabor.bedwars.map;

import de.hglabor.bedwars.Bedwars;
import de.hglabor.bedwars.region.Cuboid;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;

public class Base {

    private Team team;
    private Cuboid cuboid;

    public Base(Team team, Cuboid cuboid) {
        this.team = team;
        this.cuboid = cuboid;
    }

    public Team getTeam() {
        return team;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public static Base createByFile(File file, String mapName) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return new Base(Team.createByFile(new File(Bedwars.getPlugin().getDataFolder(), "maps/" + mapName.toLowerCase() + "/teams/" + yamlConfiguration.getString("base.team") + ".team.yml")), new Cuboid(yamlConfiguration.getLocation("base.cuboid.pos1"), yamlConfiguration.getLocation("base.cuboid.pos2")));
    }

}
