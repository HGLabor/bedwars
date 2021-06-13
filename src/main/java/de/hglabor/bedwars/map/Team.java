package de.hglabor.bedwars.map;

import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;

public class Team {

    private final Color color;
    private final String bukkitColor;
    private final String name;

    public Team(String name, Color color, String bukkitColor) {
        this.color = color;
        this.bukkitColor = bukkitColor;
        this.name = name;
    }

    public String getBukkitColor() {
        return bukkitColor;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public static Team createByFile(File file) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return new Team(yamlConfiguration.getString("team.name") ,new Color(yamlConfiguration.getInt("team.decimalColor")), yamlConfiguration.getString("team.bukkitColor"));
    }
}
