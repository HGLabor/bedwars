package de.hglabor.bedwars.config.settings;

public abstract class Setting<T> {

    public String getName() {
        return name;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    protected final String name;
    protected final T defaultValue;
    protected final Criteria criteria;

    public Setting(String name, T defaultValue, Criteria criteria) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.criteria = criteria;
    }

}
