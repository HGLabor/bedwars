package de.hglabor.bedwars.config.settings;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collection;

public enum Criteria {

    GENERIC("settings.criteria.generic", Material.EMERALD),
    COMBAT("settings.criteria.combat", Material.GOLDEN_SWORD),
    MAP("settings.criteria.map", Material.MAP),
    MECHANICS("settings.criteria.mechanics", Material.ELYTRA);

    private String translationKey;
    private Material criteriaIcon;

    Criteria(String translationKey, Material criteriaIcon) {
        this.criteriaIcon = criteriaIcon;
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public Material getIcon() {
        return this.criteriaIcon;
    }

}
