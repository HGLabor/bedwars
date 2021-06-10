package de.hglabor.bedwars.config.settings.types;

import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Setting;

public class IntSetting extends Setting<Integer> {

    private final int minValue;
    private final int maxValue;

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public IntSetting(String name, Integer defaultValue, int minValue, int maxValue, Criteria criteria) {
        super(name, defaultValue, criteria);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
