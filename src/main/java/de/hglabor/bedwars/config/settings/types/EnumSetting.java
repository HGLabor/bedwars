package de.hglabor.bedwars.config.settings.types;

import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Setting;

public class EnumSetting<E> extends Setting<E> {

    public EnumSetting(String name, E defaultValue, Criteria criteria) {
        super(name, defaultValue, criteria);
        if(!defaultValue.getClass().isEnum()) {
            throw new IllegalArgumentException("Given type is no enum");
        } else {
            this.enumClass = defaultValue.getClass();
        }
    }

    private Class<?> enumClass;

    public Class<?> getEnumClass() {
        return this.enumClass;
    }
}
