package de.hglabor.bedwars.config.settings.types;

import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Setting;
import de.hglabor.bedwars.gui.GuiBuilder;
import de.hglabor.bedwars.map.Base;

import java.util.List;

public class BasesSetting extends Setting<List<Base>> {

    private GuiBuilder listElementBuilderGuiBuilder;

    public BasesSetting(String name, List<Base> defaultValue, Criteria criteria) {
        super(name, defaultValue, criteria);
    }

}
