package de.hglabor.bedwars.config.settings;

import java.util.Collection;
import java.util.HashMap;

public class Settings {

    private static final HashMap<String, Setting<?>> settings = new HashMap<>();

    public static void addSetting(String key, Setting<?> setting) {
        SettingTask.getInstance().addSetting(setting);
        settings.put(key,setting);
    }

    public static Collection<Setting<?>> getSettings() {
        return settings.values();
    }

    public static <T> T getSetting(String key) {
        return SettingTask.getInstance().getSetting(settings.get(key));
    }

    public static <T> T getSetting(Setting<? extends T> setting) {
        return SettingTask.getInstance().getSetting(setting);
    }

    public static <V> void setSetting(Setting<? super V> setting, V value) {
        SettingTask.getInstance().setSetting(setting, value);
    }

    public static <V> void setSetting(String key, V value) {
        SettingTask.getInstance().setSetting(settings.get(key), value);
    }

}
