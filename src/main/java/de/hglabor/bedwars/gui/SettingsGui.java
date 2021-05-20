package de.hglabor.bedwars.gui;

import com.google.common.collect.ImmutableMap;
import de.hglabor.bedwars.Bedwars;
import de.hglabor.bedwars.config.localization.Locale;
import de.hglabor.bedwars.config.localization.Localization;
import de.hglabor.bedwars.config.settings.Criteria;
import de.hglabor.bedwars.config.settings.Setting;
import de.hglabor.bedwars.config.settings.SettingTask;
import de.hglabor.bedwars.config.settings.Settings;
import de.hglabor.bedwars.config.settings.types.BooleanSetting;
import de.hglabor.bedwars.config.settings.types.FloatSetting;
import de.hglabor.bedwars.gui.button.GuiButton;
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
import java.util.Collection;
import java.util.List;

public class SettingsGui {

    public static Inventory createGui(Player player) {
        GuiBuilder guiBuilder = new GuiBuilder(Bedwars.getPlugin());
        guiBuilder.withName(Localization.getMessage("settings.title", Locale.getByPlayer(player)));
        guiBuilder.withSlots(54);
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
                        GuiBuilder criteriaSettingsBuilder = new GuiBuilder(Bedwars.getPlugin());
                        criteriaSettingsBuilder.withName(criteria.name());
                        criteriaSettingsBuilder.withSlots(54);
                        for (int i = 0; i < 54; i++) {
                            criteriaSettingsBuilder.withItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), i);
                        }
                        drawCriteriaBar(criteriaSettingsBuilder, player);
                        drawButtons(criteriaSettingsBuilder, player);
                        drawSettings(criteriaSettingsBuilder, player, criteria);
                        onPress.getPlayer().closeInventory();
                        onPress.getPlayer().openInventory(criteriaSettingsBuilder.build());
                    }));
            for (int i = 9; i < 18; i++) {
                guiBuilder.withItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build(), i);
            }
        }
    }

    private static void drawButtons(GuiBuilder guiBuilder, Player player) {
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
                                player.closeInventory();
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
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10, 1);
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
                Material material = Material.BLACK_CONCRETE_POWDER;
                if(setting instanceof BooleanSetting) {
                    material = Settings.getSetting(setting) ? Material.LIME_CONCRETE : Material.RED_CONCRETE;
                } else if(setting instanceof FloatSetting) {
                    material = Material.LIGHT_BLUE_CONCRETE;
                }
                String finalLore = "";
                for (String s : createLore(setting, player)) {
                    finalLore+=s+"##";
                }
                guiBuilder.withButton(startLocation, new GuiButton(
                        setting.getName(),
                        finalLore,
                        material,
                        onPress -> {
                            if(onPress.getBukkitEvent().getClick() == ClickType.MIDDLE) {
                                SettingTask.getInstance().setSetting(setting, setting.getDefaultValue());
                                player.sendMessage(Localization.getMessage("settings.settingWasChanged", ImmutableMap.of("setting", setting.getName(), "newValue", setting.getDefaultValue().toString()), Locale.getByPlayer(player)));
                                ItemStack itemStack = onPress.getBukkitEvent().getCurrentItem();
                                ItemMeta meta = itemStack.getItemMeta();
                                meta.setLore(List.copyOf(createLore(setting, player)));
                                itemStack.setItemMeta(meta);
                                return;
                            }
                            if(setting instanceof BooleanSetting) {
                                SettingTask.getInstance().setSetting(setting, !(boolean) SettingTask.getInstance().getSetting(setting));
                                ItemStack itemStack = onPress.getBukkitEvent().getCurrentItem();
                                ItemMeta meta = itemStack.getItemMeta();
                                meta.setLore(List.copyOf(createLore(setting, player)));
                                itemStack.setItemMeta(meta);
                            }
                            if(setting instanceof FloatSetting) {
                                if(onPress.getBukkitEvent().isRightClick()) {
                                    SettingTask.getInstance().setSetting(setting, (float)SettingTask.getInstance().getSetting(setting)-0.5f);
                                } else if(onPress.getBukkitEvent().isLeftClick()) {
                                    SettingTask.getInstance().setSetting(setting, (float)SettingTask.getInstance().getSetting(setting)+0.5f);
                                }
                                ItemStack itemStack = onPress.getBukkitEvent().getCurrentItem();
                                ItemMeta meta = itemStack.getItemMeta();
                                meta.setLore(List.copyOf(createLore(setting, player)));
                                itemStack.setItemMeta(meta);
                            }
                            player.sendMessage(Localization.getMessage("settings.settingWasChanged", ImmutableMap.of("setting", setting.getName(), "newValue", SettingTask.getInstance().getSetting(setting)), Locale.getByPlayer(player)));
                        }
                ));
                startLocation++;
                if(startLocation == 35) {
                    startLocation+=2;
                }
            }
        }
    }

    private static Collection<String> createLore(Setting<?> setting, Player player) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("  ");
        lore.add(Localization.getMessage("settings.setting.defaultTooltip", ImmutableMap.of("value", setting.getDefaultValue().toString()), Locale.getByPlayer(player)));
        lore.add(Localization.getMessage("settings.setting.currentTooltip", ImmutableMap.of("value", Settings.getSetting(setting).toString()), Locale.getByPlayer(player)));
        lore.add("   ");
        if(setting instanceof BooleanSetting) {
            lore.add(Localization.getMessage("settings.setting.booleanTooltip", Locale.getByPlayer(player)));
        } else if(setting instanceof FloatSetting) {
            lore.add(ChatColor.GRAY + "Min" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + ((FloatSetting) setting).getMinValue());
            lore.add(ChatColor.GRAY + "Max" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + ((FloatSetting) setting).getMaxValue());
            lore.add("   ");
            lore.add(Localization.getMessage("settings.setting.floatTooltipOne", Locale.getByPlayer(player)));
            lore.add(Localization.getMessage("settings.setting.floatTooltipTwo", Locale.getByPlayer(player)));
        }
        lore.add(Localization.getMessage("settings.setting.resetTooltip", Locale.getByPlayer(player)));
        return lore;
    }
}
