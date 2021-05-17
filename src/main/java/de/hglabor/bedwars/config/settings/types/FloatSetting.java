package de.hglabor.bedwars.config.settings.types;

import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Setting;

public class FloatSetting extends Setting<Float> {

    private final float minValue;
    private final float maxValue;

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public FloatSetting(String name, float defaultValue, float minValue, float maxValue, Criteria criteria) {
        super(name, defaultValue, criteria);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
