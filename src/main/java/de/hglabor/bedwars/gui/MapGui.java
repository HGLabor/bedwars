package de.hglabor.bedwars.gui;


import de.hglabor.bedwars.Bedwars;
import de.hglabor.bedwars.config.localization.Locale;
import de.hglabor.bedwars.config.localization.Localization;
import de.hglabor.bedwars.gui.button.GuiButton;
import de.hglabor.bedwars.map.Map;
import de.hglabor.utils.noriskutils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class MapGui {

    public static Inventory createGui(Player player) {
        GuiBuilder guiBuilder = new GuiBuilder(Bedwars.getPlugin())
                .withName(Localization.getMessage("settings.title", Locale.getByPlayer(player)))
                .withSlots(54);
        for (int i = 0; i < 54; i++) {
            guiBuilder.withItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), i);
        }
        drawMapOverview(guiBuilder, player);
        return guiBuilder.build();
    }

    private static void drawMapOverview(GuiBuilder guiBuilder, Player player) {
        for (int i = 28; i < 44; i++) {
            if(i != 35 && i != 36) {
                guiBuilder.withItem(new ItemStack(Material.AIR), i);
            }
        }
        guiBuilder.withButton(35, new GuiButton(
                Localization.getMessage("settings.mapbuilder.mainscreen.addMapName", Locale.getByPlayer(player)),
                Localization.getMessage("settings.mapbuilder.mainscreen.addMapTooltip", Locale.getByPlayer(player)),
                Material.LIME_CONCRETE,
                onPress -> {
                    //TODO open new map screen
                }
        ));
        int start = 28;
        for (Map map : Bedwars.getMaps()) {
            guiBuilder.withButton(start, new GuiButton(
                    map.getName(),
                    Localization.getMessage("settings.mapbuilder.mainscreen.mapTooltip", Locale.getByPlayer(player)),
                    Material.LIGHT_BLUE_STAINED_GLASS,
                    onPress -> onPress.getPlayer().openInventory(MapBuilderMapManagementScreen.create(onPress.getPlayer(), map, guiBuilder))
            ));
            start++;
        }
    }

    public static class MapBuilderMapManagementScreen {

        public static Inventory create(Player player, Map map, GuiBuilder parent) {
            GuiBuilder guiBuilder = new GuiBuilder(Bedwars.getPlugin())
                    .withName(map.getName())
                    .withSlots(54);
            guiBuilder.withButton(54, new GuiButton(
                    Localization.getMessage("settings.mapbuilder.managementscreen.goBack.buttonName", Locale.getByPlayer(player)),
                    Localization.getMessage("settings.mapbuilder.managementscreen.goBack.buttonTooltip", Locale.getByPlayer(player)),
                    Material.ORANGE_STAINED_GLASS_PANE,
                    onPress -> {
                        drawMapOverview(parent, onPress.getPlayer());
                        onPress.getPlayer().openInventory(parent.build());
                    }
            ));
            guiBuilder.withButton(53, new GuiButton(
                    Localization.getMessage("settings.mapbuilder.managementscreen.delete.buttonName", Locale.getByPlayer(player)),
                    Localization.getMessage("settings.mapbuilder.managementscreen.delete.buttonTooltip", Locale.getByPlayer(player)),
                    Material.RED_STAINED_GLASS_PANE,
                    onPress -> {
                        Bedwars.registerMap(map);
                        File mapFile = new File(Bedwars.getPlugin().getDataFolder(), "maps/" + map.getName().toLowerCase() + "/");
                        mapFile.delete();
                        drawMapOverview(parent, onPress.getPlayer());
                        onPress.getPlayer().openInventory(parent.build());
                    }
            ));
            return guiBuilder.build();
        }

    }

}
