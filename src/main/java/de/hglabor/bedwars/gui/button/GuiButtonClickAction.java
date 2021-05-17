package de.hglabor.bedwars.gui.button;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiButtonClickAction {

    private final InventoryClickEvent bukkitEvent;
    private final Player player;
    private final GuiButton button;

    public GuiButtonClickAction(Player player, GuiButton button, InventoryClickEvent bukkitEvent) {
        this.player = player;
        this.button = button;
        this.bukkitEvent = bukkitEvent;
    }

    public InventoryClickEvent getBukkitEvent() {
        return bukkitEvent;
    }

    public Player getPlayer() {
        return player;
    }

    public GuiButton getButton() {
        return button;
    }
}
