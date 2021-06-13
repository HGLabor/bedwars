package de.hglabor.bedwars.gui.button;

import de.hglabor.utils.noriskutils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;

public  class GuiButton {

    private Consumer<GuiButtonClickAction> onPress;
    private ItemStack itemStackResult;

    public void press(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1,1);
        onPress.accept(new GuiButtonClickAction(player, this, event));
    }

    public GuiButton(String name, String description, Material icon, Consumer<GuiButtonClickAction> onPress) {
        this.onPress = onPress;
        this.itemStackResult = new ItemBuilder(icon).setName(name.replace("&", "§")).setDescription(description.replace("&", "§").split("##")).build();
    }

    public GuiButton(String name, String description, ItemStack icon, Consumer<GuiButtonClickAction> onPress) {
        this.onPress = onPress;
        this.itemStackResult = new ItemBuilder(icon).setName(name.replace("&", "§")).setDescription(description.replace("&", "§").split("##")).build();
    }

    public GuiButton(String name, String description, ItemBuilder icon, Consumer<GuiButtonClickAction> onPress) {
        this.onPress = onPress;
        this.itemStackResult = new ItemBuilder(icon.build()).setName(name.replace("&", "§")).setDescription(description.replace("&", "§").split("##")).build();
    }

    public ItemStack getItemStackResult() {
        return itemStackResult;
    }
}
