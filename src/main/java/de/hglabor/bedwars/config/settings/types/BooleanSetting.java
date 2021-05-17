package de.hglabor.bedwars.config.settings.types;

import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Setting;

public class BooleanSetting extends Setting<Boolean> {

    public BooleanSetting(String name, Boolean defaultValue, Criteria criteria) {
        super(name, defaultValue, criteria);
    }
}
