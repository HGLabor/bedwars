package de.hglabor.bedwars.config.settings;

import java.util.HashMap;

public class SettingTask {

    private static final SettingTask instance = new SettingTask();

    public static SettingTask getInstance() {
        return instance;
    }

    private static HashMap<Setting<?>, Object> settingsMap = new HashMap<>();

    public void addSetting(Setting<?> setting) {
        settingsMap.put(setting, setting.getDefaultValue());
    }

    @SuppressWarnings("unchecked")
    public <T> T getSetting(Setting<?> setting) {
        return (T) settingsMap.getOrDefault(setting, setting.getDefaultValue());
    }

    public <V> void setSetting(Setting<?> setting, V value) {
        settingsMap.put(setting, value);
    }


}
