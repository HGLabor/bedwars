package de.hglabor.bedwars.gui;

import com.google.common.collect.ImmutableMap;
import de.hglabor.bedwars.Bedwars;
import de.hglabor.bedwars.config.localization.Locale;
import de.hglabor.bedwars.config.localization.Localization;
import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Setting;
import de.hglabor.bedwars.config.settings.SettingTask;
import de.hglabor.bedwars.config.settings.Settings;
import de.hglabor.bedwars.config.settings.types.*;
import de.hglabor.bedwars.gui.button.GuiButton;
import de.hglabor.bedwars.utils.MathUtils;
import de.hglabor.utils.noriskutils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SettingsGui {

    public static Inventory createGui(Player player) {
        GuiBuilder guiBuilder = new GuiBuilder(Bedwars.getPlugin())
            .withName(Localization.getMessage("settings.title", Locale.getByPlayer(player)))
            .withSlots(54);
        for (int i = 0; i < 54; i++) {
            guiBuilder.withItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), i);
        }
        drawCriteriaBar(guiBuilder, player);
        drawButtons(guiBuilder, player);
        return guiBuilder.build();
    }

    private static void drawCriteriaBar(GuiBuilder guiBuilder, Player player) {
        int currentCriteriaSlot = 0;
        for (Criteria criteria : Criteria.values()) {
            currentCriteriaSlot++;
            int availableSettings = 0;
            for (Setting<?> setting : Settings.getSettings()) {
                if(setting.getCriteria() == criteria) {
                    availableSettings++;
                }
            }
            guiBuilder.withButton(currentCriteriaSlot, new GuiButton(
                Localization.getMessage(criteria.getTranslationKey(), Locale.getByPlayer(player)),
                Localization.getMessage("settings.criteriaSelector.tooltip", ImmutableMap.of("settingCount", String.valueOf(availableSettings)), Locale.getByPlayer(player)),
                criteria.getIcon(),
                onPress -> {
                    if (player.getOpenInventory().getTitle().equals(criteria.name()))
                        return; // Or else the GUI breaks
                    GuiBuilder criteriaSettingsBuilder = new GuiBuilder(Bedwars.getPlugin());
                    criteriaSettingsBuilder.withName(criteria.name());
                    criteriaSettingsBuilder.withSlots(54);
                    for (int i = 0; i < 54; i++) {
                        criteriaSettingsBuilder.withItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), i);
                    }
                    drawCriteriaBar(criteriaSettingsBuilder, player);
                    drawButtons(criteriaSettingsBuilder, player);
                    drawSettings(criteriaSettingsBuilder, player, criteria);
                    //onPress.getPlayer().closeInventory(); (Makes cursor pos reset)
                    onPress.getPlayer().openInventory(criteriaSettingsBuilder.build());
                }));
            for (int i = 9; i < 18; i++) {
                guiBuilder.withItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build(), i);
            }
        }
    }

    private static void drawButtons(GuiBuilder guiBuilder, Player player) {
        guiBuilder.withButton(45, new GuiButton(
                Localization.getMessage("settings.button.maps.title", Locale.getByPlayer(player)),
                Localization.getMessage("settings.button.maps.description", Locale.getByPlayer(player)),
                Material.MAP,
                onPress -> onPress.getPlayer().openInventory(MapGui.createGui(player))
        ));
        guiBuilder.withButton(52, new GuiButton(
                Localization.getMessage("settings.button.resetAndExit.title", Locale.getByPlayer(player)),
                Localization.getMessage("settings.button.resetAndExit.description", Locale.getByPlayer(player)),
                Material.RED_STAINED_GLASS_PANE,
                onPress -> {
                    GuiBuilder selectionBuilder = new GuiBuilder(Bedwars.getPlugin());
                    selectionBuilder.withSlots(27);
                    for (int i = 0; i < 27; i++) {
                        selectionBuilder.withItem(new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setName(" ").build(), i);
                    }
                    selectionBuilder.withButton(13, new GuiButton(
                            Localization.getMessage("settings.button.confirmReset.title", Locale.getByPlayer(player)),
                            Localization.getMessage("settings.button.confirmReset.description", Locale.getByPlayer(player)),
                            Material.LIME_CONCRETE,
                            clickAction -> {
                                for (Setting<?> setting : Settings.getSettings()) {
                                    SettingTask.getInstance().setSetting(setting, setting.getDefaultValue());
                                }
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                                player.sendMessage(Localization.getMessage("settings.resetted", ImmutableMap.of("count", String.valueOf(Settings.getSettings().size())), Locale.getByPlayer(player)));
                            }
                    ));
                    selectionBuilder.withName("RESET CONFIRMATION");
                    player.closeInventory();
                    player.openInventory(selectionBuilder.build());
                }
        ));
        guiBuilder.withButton(53, new GuiButton(
                Localization.getMessage("settings.button.applyAndExit.title", Locale.getByPlayer(player)),
                Localization.getMessage("settings.button.applyAndExit.description", Locale.getByPlayer(player)),
                Material.LIME_STAINED_GLASS_PANE,
                onPress -> {
                    GuiBuilder selectionBuilder = new GuiBuilder(Bedwars.getPlugin());
                    selectionBuilder.withSlots(27);
                    for (int i = 0; i < 27; i++) {
                        selectionBuilder.withItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), i);
                    }
                    selectionBuilder.withButton(10, new GuiButton(
                            Localization.getMessage("settings.button.applyToEveryRound.title", Locale.getByPlayer(player)),
                            Localization.getMessage("settings.button.applyToEveryRound.description", Locale.getByPlayer(player)),
                            Material.LIME_DYE,
                            clickAction -> {
                                //write settings to config in template
                                player.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1, 1);
                                player.sendMessage(Localization.getMessage("settings.saved", Locale.getByPlayer(player)));
                            }
                    ));
                    selectionBuilder.withButton(16, new GuiButton(
                            Localization.getMessage("settings.button.applyToOnlyThisRound.title", Locale.getByPlayer(player)),
                            Localization.getMessage("settings.button.applyToOnlyThisRound.description", Locale.getByPlayer(player)),
                            Material.ORANGE_DYE,
                            clickAction -> {
                                player.closeInventory();
                                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
                                player.sendMessage(Localization.getMessage("settings.saved", Locale.getByPlayer(player)));
                            }
                    ));
                    selectionBuilder.withName("APPLY CONFIRMATION");
                    player.closeInventory();
                    player.openInventory(selectionBuilder.build());
                    //player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1); // Double Sound intended? No XD
                }
        ));
    }

    private static void drawSettings(GuiBuilder guiBuilder, Player player, Criteria criteria) {
        int startLocation = 28;
        for (int i = 28; i < 44; i++) {
            if(i != 35 && i != 36) {
                guiBuilder.withItem(new ItemStack(Material.AIR), i);
            }
        }
        for (Setting<?> setting : Settings.getSettings()) {
            if(setting.getCriteria() == criteria) {
                StringBuilder finalLore = new StringBuilder();
                for (String s : createLore(setting, player)) {
                    finalLore.append(s);
                    finalLore.append("##");
                }
                guiBuilder.withButton(startLocation, new GuiButton(
                    setting.getName(),
                    finalLore.toString(),
                    materialForSetting(setting),
                    onPress -> {
                        Object newValue = null;
                        if (onPress.getBukkitEvent().getClick() == ClickType.MIDDLE) {
                            newValue = setting.getDefaultValue();
                        } else if (setting instanceof BooleanSetting) {
                            newValue = !(boolean) SettingTask.getInstance().getSetting(setting);
                        } else if (setting instanceof IntSetting) {
                            int current = SettingTask.getInstance().getSetting(setting);
                            if (onPress.getBukkitEvent().isRightClick()) {
                                if (current > ((IntSetting) setting).getMinValue()) {
                                    newValue = current - 1;
                                }
                            } else if (onPress.getBukkitEvent().isLeftClick()) {
                                if (current < ((IntSetting) setting).getMaxValue()) {
                                    newValue = current + 1;
                                }
                            }
                        } else if (setting instanceof FloatSetting) {
                            float current = SettingTask.getInstance().getSetting(setting);
                            if (onPress.getBukkitEvent().isRightClick()) {
                                if (current - 0.5f >= ((FloatSetting) setting).getMinValue())
                                    newValue = current - 0.5f;
                            } else if (onPress.getBukkitEvent().isLeftClick()) {
                                if (current + 0.5f <= ((FloatSetting) setting).getMaxValue())
                                    newValue = current + 0.5f;
                            }
                        } else if (setting instanceof DoubleSetting) {
                            double current = SettingTask.getInstance().getSetting(setting);
                            if (onPress.getBukkitEvent().isRightClick()) {
                                if (current - 0.5d >= ((DoubleSetting) setting).getMinValue())
                                    newValue = current - 0.5d;
                            } else if (onPress.getBukkitEvent().isLeftClick()) {
                                if (current + 0.5d <= ((DoubleSetting) setting).getMaxValue())
                                    newValue = current + 0.5d;
                            }
                        } else if (setting instanceof EnumSetting<?>) {
                            drawEnumSetting((EnumSetting<?>) setting, player, 0, guiBuilder);
                        }
                        if (newValue == null) {
                            return;
                        }
                        SettingTask.getInstance().setSetting(setting, newValue);
                        ItemStack itemStack = onPress.getBukkitEvent().getCurrentItem();
                        ItemMeta meta = itemStack.getItemMeta();
                        meta.setLore(createLore(setting, player));
                        itemStack.setItemMeta(meta);
                        itemStack.setType(materialForSetting(setting));
                        player.sendMessage(Localization.getMessage("settings.settingWasChanged", ImmutableMap.of("setting", setting.getName(), "newValue", SettingTask.getInstance().getSetting(setting).toString()), Locale.getByPlayer(player)));
                    }
                ));
                startLocation++;
                if(startLocation == 35) {
                    startLocation+=2;
                }
            }
        }
    }

    private static void drawEnumSetting(EnumSetting<?> setting, Player player, int page /* = 0 */, GuiBuilder oldGui) {
        Enum<?>[] entries = (Enum<?>[]) (setting).getEnumClass().getEnumConstants();
        int pages = entries.length <= 54 ? 0 : (entries.length - 2) / 52;

        if (page > pages)
            return;

        GuiBuilder enumSettingScreen = new GuiBuilder(Bedwars.getPlugin())
                .withName(setting.getName() + (pages == 0 ? " Selector" : " Selector (" + page + "/" + pages + ")"));

        int pageSize = pages == 0 ? entries.length : page == 0 ? 53 : page == pages ? entries.length % 52 - 1 : 52;
        enumSettingScreen.withSlots(pageSize);

        if (page > 0)
            enumSettingScreen.withButton(0, new GuiButton(
                    Localization.getMessage("settings.pageUp", Locale.getByPlayer(player)),
                    Localization.getMessage("settings.pageUpTooltip", Locale.getByPlayer(player)),
                    Material.ARROW,
                    it -> drawEnumSetting(setting, player, page - 1, oldGui)
            ));
        if (page < pages)
            enumSettingScreen.withButton(53, new GuiButton(
                    Localization.getMessage("settings.pageDown", Locale.getByPlayer(player)),
                    Localization.getMessage("settings.pageDownTooltip", Locale.getByPlayer(player)),
                    Material.ARROW,
                    it -> drawEnumSetting(setting, player, page + 1, oldGui)
            ));

        Enum<?> current = SettingTask.getInstance().getSetting(setting);

        for (int i = page == 0 ? 0 : 1, e = page == 0 ? 0 : page * 52 + 1; i < (page == 0 ? pageSize : pageSize + 1); ++i, ++e) {
            Enum<?> entry = entries[e];
            enumSettingScreen.withButton(i, new GuiButton(
                    entry.name(),
                    Localization.getMessage("settings.pageDown", Locale.getByPlayer(player)),
                    current.equals(entry) ? Material.FILLED_MAP : Material.PAPER,
                    it -> {
                        SettingTask.getInstance().setSetting(setting, entry);
                        drawSettings(oldGui, player, setting.getCriteria()); // Else it doesn't update "current"
                        player.openInventory(oldGui.build());
                    }
            ));
        }

        player.openInventory(enumSettingScreen.build());
    }

    private static List<String> createLore(Setting<?> setting, Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("  ");
        lore.add(Localization.getMessage("settings.setting.defaultTooltip", ImmutableMap.of("value", setting.getDefaultValue().toString()), Locale.getByPlayer(player)));
        lore.add(Localization.getMessage("settings.setting.currentTooltip", ImmutableMap.of("value", Settings.getSetting(setting).toString()), Locale.getByPlayer(player)));
        lore.add("   ");
        if (setting instanceof BooleanSetting) {
            lore.add(Localization.getMessage("settings.setting.booleanTooltip", Locale.getByPlayer(player)));
        } else if (setting instanceof IntSetting) {
            lore.add(ChatColor.GRAY + "Min" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + ((IntSetting) setting).getMinValue());
            lore.add(ChatColor.GRAY + "Max" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + ((IntSetting) setting).getMaxValue());
            lore.add("   ");
            lore.add(Localization.getMessage("settings.setting.intTooltipOne", Locale.getByPlayer(player)));
            lore.add(Localization.getMessage("settings.setting.intTooltipTwo", Locale.getByPlayer(player)));
        } else if (setting instanceof FloatSetting) {
            lore.add(ChatColor.GRAY + "Min" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + ((FloatSetting) setting).getMinValue());
            lore.add(ChatColor.GRAY + "Max" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + ((FloatSetting) setting).getMaxValue());
            lore.add("   ");
            lore.add(Localization.getMessage("settings.setting.floatTooltipOne", Locale.getByPlayer(player)));
            lore.add(Localization.getMessage("settings.setting.floatTooltipTwo", Locale.getByPlayer(player)));
        } else if (setting instanceof DoubleSetting) {
            lore.add(ChatColor.GRAY + "Min" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + ((DoubleSetting) setting).getMinValue());
            lore.add(ChatColor.GRAY + "Max" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + ((DoubleSetting) setting).getMaxValue());
            lore.add("   ");
            lore.add(Localization.getMessage("settings.setting.doubleTooltipOne", Locale.getByPlayer(player)));
            lore.add(Localization.getMessage("settings.setting.doubleTooltipTwo", Locale.getByPlayer(player))); // Is in necessary to have separate float / double tooltips?
        }
        lore.add(Localization.getMessage("settings.setting.resetTooltip", Locale.getByPlayer(player)));
        return lore;
    }

    private static Material materialForSetting(Setting<?> setting) {
        Material material = Material.BLACK_CONCRETE_POWDER;
        if (setting instanceof BooleanSetting) {
            material = (boolean) Settings.getSetting(setting) ? Material.LIME_CONCRETE : Material.RED_CONCRETE;
        } else if (setting instanceof IntSetting) {
            material = Material.BLUE_CONCRETE;
        } else if (setting instanceof FloatSetting || setting instanceof DoubleSetting) {
            material = Material.LIGHT_BLUE_CONCRETE;
        } else if (setting instanceof EnumSetting<?>) {
            material = Material.BOOK;
        }
        return material;
    }
}
