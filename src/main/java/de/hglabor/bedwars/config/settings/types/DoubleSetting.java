package de.hglabor.bedwars.config.settings.types;

import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Setting;

public class DoubleSetting extends Setting<Double> {

    private final double minValue;
    private final double maxValue;

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public DoubleSetting(String name, double defaultValue, double minValue, double maxValue, Criteria criteria) {
        super(name, defaultValue, criteria);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
