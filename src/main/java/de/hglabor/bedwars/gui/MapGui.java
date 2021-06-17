package de.hglabor.bedwars.gui;


import com.google.common.collect.ImmutableMap;
import de.hglabor.bedwars.Bedwars;
import de.hglabor.bedwars.config.localization.Locale;
import de.hglabor.bedwars.config.localization.Localization;
import de.hglabor.bedwars.entity.GameEntity;
import de.hglabor.bedwars.entity.entities.ItemShopVillagerEntity;
import de.hglabor.bedwars.entity.entities.UpgradeShopVillagerEntity;
import de.hglabor.bedwars.gui.button.GuiButton;
import de.hglabor.bedwars.map.Base;
import de.hglabor.bedwars.map.Map;
import de.hglabor.bedwars.map.Spawner;
import de.hglabor.bedwars.map.builder.MapFactory;
import de.hglabor.utils.noriskutils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;

public class MapGui {

    public static MapFactory currentlyBuilding;

    public static Inventory createGui(Player player) {
        GuiBuilder guiBuilder = new GuiBuilder(Bedwars.getPlugin())
                .withName(Localization.getMessage("settings.maps", Locale.getByPlayer(player)))
                .withSlots(54);
        for (int i = 0; i < 54; i++) {
            guiBuilder.withItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), i);
        }
        drawMapOverview(guiBuilder, player);
        return guiBuilder.build();
    }

    private static void drawMapOverview(GuiBuilder guiBuilder, Player player) {
        for (int i = 10; i < 44; i++) {
            if(i != 35 && i != 36 && i != 17 && i != 18 && i != 26 && i != 27) {
                guiBuilder.withItem(new ItemStack(Material.AIR), i);
            }
        }
        guiBuilder.withButton(53, new GuiButton(
                Localization.getMessage("settings.mapbuilder.mainscreen.addMapName", Locale.getByPlayer(player)),
                Localization.getMessage("settings.mapbuilder.mainscreen.addMapTooltip", Locale.getByPlayer(player)),
                Material.LIME_CONCRETE,
                onPress -> onPress.getPlayer().openInventory(MapBuilderMainScreen.createGui(player))
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
                    .withName(ChatColor.BLACK + map.getName().toUpperCase() + " OVERVIEW")
                    .withSlots(54);
            for (int i = 10; i < 44; i++) {
                if(i != 35 && i != 36 && i != 17 && i != 18 && i != 26 && i != 27) {
                    guiBuilder.withItem(new ItemStack(Material.AIR), i);
                }
            }
            int start = 30;
            guiBuilder.withItem(new ItemBuilder(Material.YELLOW_CONCRETE).setName(Localization.getMessage("settings.mapbuilder.managementscreen.size.buttonName", Locale.getByPlayer(player))).setDescription(Localization.getMessage("settings.mapbuilder.managementscreen.size.buttonTooltip", ImmutableMap.of("size", String.valueOf(map.getSize())), Locale.getByPlayer(player)).split("##")).build(), 28);
            guiBuilder.withItem(new ItemBuilder(Material.YELLOW_CONCRETE).setName(Localization.getMessage("settings.mapbuilder.managementscreen.teamSize.buttonName", Locale.getByPlayer(player))).setDescription(Localization.getMessage("settings.mapbuilder.managementscreen.teamSize.buttonTooltip", ImmutableMap.of("teamSize", String.valueOf(map.getTeamSize())), Locale.getByPlayer(player)).split("##")).build(), 29);
            for (Spawner spawner : map.getSpawners()) {
                guiBuilder.withItem(
                        new ItemBuilder(Material.SPAWNER).setName(spawner.getMaterial() + " SPAWNER").setDescription(
                                "" + ChatColor.GRAY + "tickDelayBetweenSpawns" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + spawner.getTickDelayBetweenSpawns(),
                                "" + ChatColor.GRAY + "tickRate" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + spawner.getSpawnTickRate(),
                                "" + ChatColor.GRAY + "xyz" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + spawner.getLocation().getBlockX() + " " + spawner.getLocation().getBlockY() + " " + spawner.getLocation().getBlockZ()
                        ).build(), start);
                start++;
            }
            for (GameEntity<?> entity : map.getEntities()) {
                guiBuilder.withItem(
                        new ItemBuilder(Material.SPAWNER).setName(entity.getClass().getName() + " Entity").setDescription(
                                "" + ChatColor.GRAY + "Name" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + entity.getName(),
                                "" + ChatColor.GRAY + "Origin" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + entity.getEntityClazz().getName(),
                                "" + ChatColor.GRAY + "xyz" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + entity.getLocation().getBlockX() + " " + entity.getLocation().getBlockY() + " " + entity.getLocation().getBlockZ()
                        ).build(), start);
                start++;
            }
            for (Base base : map.getBases()) {
                guiBuilder.withItem(
                        new ItemBuilder(Material.SPAWNER).setName("Base from Team " + net.md_5.bungee.api.ChatColor.of(base.getTeam().getColor()) + base.getTeam().getColor().getRGB()).setDescription(
                                "" + ChatColor.GRAY + "xyz (pos1)" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + base.getCuboid().getFirst().getBlockX() + " " + base.getCuboid().getFirst().getBlockY() + " " + base.getCuboid().getFirst().getBlockZ(),
                                "" + ChatColor.GRAY + "xyz (pos2)" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + base.getCuboid().getSecond().getBlockX() + " " + base.getCuboid().getSecond().getBlockY() + " " + base.getCuboid().getSecond().getBlockZ()
                        ).build(), start);
                start++;
            }
            guiBuilder.withButton(53, new GuiButton(
                    Localization.getMessage("settings.mapbuilder.managementscreen.goBack.buttonName", Locale.getByPlayer(player)),
                    Localization.getMessage("settings.mapbuilder.managementscreen.goBack.buttonTooltip", Locale.getByPlayer(player)),
                    Material.ORANGE_STAINED_GLASS_PANE,
                    onPress -> {
                        drawMapOverview(parent, onPress.getPlayer());
                        onPress.getPlayer().openInventory(parent.build());
                    }
            ));
            guiBuilder.withButton(52, new GuiButton(
                    Localization.getMessage("settings.mapbuilder.managementscreen.delete.buttonName", Locale.getByPlayer(player)),
                    Localization.getMessage("settings.mapbuilder.managementscreen.delete.buttonTooltip", Locale.getByPlayer(player)),
                    Material.RED_STAINED_GLASS_PANE,
                    onPress -> {
                        Bedwars.unregisterMap(map);
                        File mapFile = new File(Bedwars.getPlugin().getDataFolder(), "maps/" + map.getName().toLowerCase() + "/");
                        mapFile.delete();
                        drawMapOverview(parent, onPress.getPlayer());
                        onPress.getPlayer().openInventory(parent.build());
                    }
            ));
            return guiBuilder.build();
        }
    }

    public static class MapBuilderMainScreen {

        public static Inventory createGui(Player player) {
            if(currentlyBuilding == null) {
                currentlyBuilding = new MapFactory();
            }
            GuiBuilder guiBuilder = new GuiBuilder(Bedwars.getPlugin());
            guiBuilder.withName(ChatColor.BLACK + "MAP BUILDER OVERVIEW");
            guiBuilder.withSlots(27);
            for (int i = 0; i < 26; i++) {
                guiBuilder.withItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), i);
            }
            guiBuilder.withButton(26, new GuiButton(
                    Localization.getMessage("settings.mapbuilder.managementscreen.goBack.buttonName", Locale.getByPlayer(player)),
                    Localization.getMessage("settings.mapbuilder.managementscreen.goBack.buttonTooltip", Locale.getByPlayer(player)),
                    Material.SPECTRAL_ARROW,
                    onPress -> onPress.getPlayer().openInventory(SettingsGui.createGui(player))
            ));
            guiBuilder.withButton(9, new GuiButton(
                    ChatColor.LIGHT_PURPLE + "Name",
                    Localization.getMessage("settings.mapbuilder.buildermainscreen.name.buttonTooltip", Locale.getByPlayer(player)),
                    Material.NAME_TAG,
                    onPress -> {

                    }
            ));
            guiBuilder.withButton(10, new GuiButton(
                    ChatColor.AQUA + "Teams",
                    Localization.getMessage("settings.mapbuilder.buildermainscreen.teams.buttonTooltip", Locale.getByPlayer(player)),
                    Material.LIGHT_BLUE_BANNER,
                    onPress -> {

                    }
            ));
            guiBuilder.withButton(11, new GuiButton(
                    ChatColor.GOLD + "Bases",
                    Localization.getMessage("settings.mapbuilder.buildermainscreen.bases.buttonTooltip", Locale.getByPlayer(player)),
                    Material.ORANGE_STAINED_GLASS,
                    onPress -> {

                    }
            ));
            guiBuilder.withButton(12, new GuiButton(
                    ChatColor.DARK_GREEN + "Spawners",
                    Localization.getMessage("settings.mapbuilder.buildermainscreen.spawners.buttonTooltip", Locale.getByPlayer(player)),
                    Material.SPAWNER,
                    onPress -> {

                    }
            ));
            guiBuilder.withButton(13, new GuiButton(
                    ChatColor.YELLOW + "Entities",
                    Localization.getMessage("settings.mapbuilder.buildermainscreen.entites.buttonTooltip", Locale.getByPlayer(player)),
                    Material.BLAZE_SPAWN_EGG,
                    onPress -> {

                    }
            ));
            guiBuilder.withButton(14, new GuiButton(
                    ChatColor.BLUE + "Map Size (Base count)",
                    Localization.getMessage("settings.mapbuilder.buildermainscreen.baseCount.buttonTooltip", Locale.getByPlayer(player)),
                    Material.BLUE_CONCRETE,
                    onPress -> {
                        ClickType click = onPress.getBukkitEvent().getClick();
                        if(click.isLeftClick()) {
                            currentlyBuilding.setSize(currentlyBuilding.getSize()+1);
                        } else if(click.isRightClick()) {
                            currentlyBuilding.setSize(currentlyBuilding.getSize()-1);
                        } else if(click == ClickType.MIDDLE) {
                            currentlyBuilding.setSize(1);
                        }
                        updateLore(onPress.getBukkitEvent().getCurrentItem(), Localization.getMessage("settings.mapbuilder.buildermainscreen.baseCount.buttonTooltip", ImmutableMap.of("current", "" + currentlyBuilding.getTeamSize()), Locale.getByPlayer(player)).split("##"));
                    }
            ));
            guiBuilder.withButton(15, new GuiButton(
                    ChatColor.BLUE + "Team Size",
                    Localization.getMessage("settings.mapbuilder.buildermainscreen.teamCount.buttonTooltip", ImmutableMap.of("current", "" + currentlyBuilding.getTeamSize()), Locale.getByPlayer(player)),
                    Material.BLUE_CONCRETE,
                    onPress -> {
                        ClickType click = onPress.getBukkitEvent().getClick();
                        if(click.isLeftClick()) {
                            currentlyBuilding.setTeamSize(currentlyBuilding.getTeamSize()+1);
                        } else if(click.isRightClick()) {
                            currentlyBuilding.setTeamSize(currentlyBuilding.getTeamSize()-1);
                        } else if(click == ClickType.MIDDLE) {
                            currentlyBuilding.setTeamSize(1);
                        }
                        updateLore(onPress.getBukkitEvent().getCurrentItem(), Localization.getMessage("settings.mapbuilder.buildermainscreen.teamCount.buttonTooltip", ImmutableMap.of("current", "" + currentlyBuilding.getTeamSize()), Locale.getByPlayer(player)).split("##"));
                    }
            ));
            return guiBuilder.build();
        }

        private static void updateLore(ItemStack stack, String... newLore) {
            ItemMeta meta = stack.getItemMeta();
            meta.setLore(List.of(newLore));
            stack.setItemMeta(meta);
        }
    }

    public static class MapBuilderEntitiesOverviewScreen {

        public static Inventory createGui(Player player) {
            GuiBuilder guiBuilder = new GuiBuilder(Bedwars.getPlugin())
                    .withName(ChatColor.BLACK + "MAP BUILDER ENTITY OVERVIEW")
                    .withSlots(54);
            for (int i = 10; i < 44; i++) {
                if(i != 35 && i != 36 && i != 17 && i != 18 && i != 26 && i != 27) {
                    guiBuilder.withItem(new ItemStack(Material.AIR), i);
                }
            }
            int start = 10;
            int itemShopCount = 1;
            int upgradeShopCount = 1;
            for (ItemShopVillagerEntity itemShop : currentlyBuilding.getItemShops()) {
                guiBuilder.withButton(start, new GuiButton(
                        ChatColor.DARK_GREEN + "Item Shop #" + itemShopCount,
                        "" + ChatColor.GRAY + "xyz" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + itemShop.getLocation().getBlockX() + " " + itemShop.getLocation().getBlockY() + " " + itemShop.getLocation().getBlockZ(),
                        Material.GREEN_CONCRETE_POWDER,
                        onPress -> {
                            itemShop.kill();
                            currentlyBuilding.removeItemShop(itemShop);
                        }
                ));
                start++;
                itemShopCount++;
            }
            for (UpgradeShopVillagerEntity upgradeShopVillagerEntity : currentlyBuilding.getUpgradeShops()) {
                guiBuilder.withButton(start, new GuiButton(
                        ChatColor.YELLOW + "Upgrade Shop #" + upgradeShopCount,
                        "" + ChatColor.GRAY + "xyz" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + upgradeShopVillagerEntity.getLocation().getBlockX() + " " + upgradeShopVillagerEntity.getLocation().getBlockY() + " " + upgradeShopVillagerEntity.getLocation().getBlockZ(),
                        Material.YELLOW_CONCRETE_POWDER,
                        onPress -> {
                            upgradeShopVillagerEntity.kill();
                            currentlyBuilding.removeUpgradeShop(upgradeShopVillagerEntity);
                        }
                ));
                start++;
                upgradeShopCount++;
            }
            return guiBuilder.build();
        }

    }

}
