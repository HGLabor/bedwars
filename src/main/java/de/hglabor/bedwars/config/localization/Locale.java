package de.hglabor.bedwars.config.localization;

import org.bukkit.entity.Player;

import java.io.File;

public enum Locale {

    GERMAN("de"),
    ENGLISH("en");

    Locale(String alias) {
        this.alias = alias;
    }

    private String alias;

    public String getAlias() {
        return alias;
    }

    public static Locale getByAlias(String alias) {
        for (Locale locale : Locale.values()) {
            if(locale.getAlias().equalsIgnoreCase(alias)) {
                return locale;
            }
        }
        return Locale.ENGLISH;
    }

    public static Locale getByFile(File file) {
        String alias = file.getName().replace(".locale.yml", "");
        for (Locale locale : Locale.values()) {
            if(locale.getAlias().equalsIgnoreCase(alias)) {
                return locale;
            }
        }
        return Locale.ENGLISH;
    }

    public static Locale getByPlayer(Player player) {
        String alias = player.getLocale().split("_")[0];
        for (Locale locale : Locale.values()) {
            if(locale.getAlias().equalsIgnoreCase(alias)) {
                return locale;
            }
        }
        return Locale.ENGLISH;
    }

}
