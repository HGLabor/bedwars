package de.hglabor.bedwars.map.builder;

import org.bukkit.Material;

public enum SpawnerMaterial {

    IRON(Material.IRON_INGOT),
    GOLD(Material.GOLD_INGOT),
    COAL(Material.COAL),
    EMERALD(Material.EMERALD),
    DIAMOND(Material.DIAMOND),
    NETHERITE(Material.NETHERITE_INGOT);

    private Material material;

    SpawnerMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return this.material;
    }

}
