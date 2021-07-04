package de.hglabor.bedwars;

import de.hglabor.bedwars.commands.SettingsCommand;
import de.hglabor.bedwars.config.localization.Localization;
import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Settings;
import de.hglabor.bedwars.config.settings.types.BooleanSetting;
import de.hglabor.bedwars.config.settings.types.EnumSetting;
import de.hglabor.bedwars.config.settings.types.FloatSetting;
import de.hglabor.bedwars.config.settings.types.IntSetting;
import de.hglabor.bedwars.map.Map;
import net.axay.kspigot.main.KSpigot;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bedwars extends KSpigot {

    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    private static final List<Map> maps = new ArrayList<>();

    public static Collection<Map> getMaps() {
        return maps;
    }

    public static void registerMap(Map map) {
        maps.add(map);
    }

    public static void unregisterMap(Map map) {
        map.destroy();
        maps.remove(map);
    }
    @Override
    public void startup() {
        plugin = this;
        Localization.init();
        getCommand("settings").setExecutor(new SettingsCommand());
        Settings.addSetting("testFloatSetting", new FloatSetting("Test FloatSetting", 5f, 0f, 23f, Criteria.COMBAT));
        Settings.addSetting("testBooleanSetting", new BooleanSetting("Test BooleanSetting", false, Criteria.MAP));
        Settings.addSetting("testIntSetting", new IntSetting("Test IntSetting", 5, 0, 12, Criteria.MECHANICS));
        Settings.addSetting("testEnumSetting", new EnumSetting<>("Test EnumSetting", BlockFace.EAST, Criteria.GENERIC));
        Settings.addSetting("testLargeEnumSetting", new EnumSetting<>("Test Large EnumSetting", Material.AIR, Criteria.SHOP));
    }

    @Override
    public void shutdown() {

    }
}
