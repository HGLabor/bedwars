package de.hglabor.bedwars.map;

import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;

public class Team {

    private final Color color;

    public Team(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static Team createByFile(File file) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return new Team(new Color(yamlConfiguration.getInt("team.decimalColor")));
    }
}
