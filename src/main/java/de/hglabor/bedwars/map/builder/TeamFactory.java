package de.hglabor.bedwars.map.builder;

import de.hglabor.bedwars.map.Team;

import java.awt.*;

public class TeamFactory {

    private Color color;
    private String bukkitColor;
    private String name;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getBukkitColor() {
        return bukkitColor;
    }

    public void setBukkitColor(String bukkitColor) {
        this.bukkitColor = bukkitColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team constructOne() {
        return new Team(name, color, bukkitColor);
    }
}
