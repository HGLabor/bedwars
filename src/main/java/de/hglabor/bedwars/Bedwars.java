package de.hglabor.bedwars;

import de.hglabor.bedwars.commands.SettingsCommand;
import de.hglabor.bedwars.config.localization.Localization;
import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Settings;
import de.hglabor.bedwars.config.settings.types.BooleanSetting;
import de.hglabor.bedwars.config.settings.types.FloatSetting;
import de.hglabor.bedwars.listener.PacketListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Bedwars extends JavaPlugin {

    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        Localization.init();
        getCommand("settings").setExecutor(new SettingsCommand());
        Settings.addSetting("testFloatSetting", new FloatSetting("Test FloatSetting", 5f, 0f, 23f, Criteria.COMBAT));
        Settings.addSetting("testBooleanSetting", new BooleanSetting("Test BooleanSetting", false, Criteria.MAP));
        new PacketListener(this);
    }

    @Override
    public void onDisable() {

    }
}
