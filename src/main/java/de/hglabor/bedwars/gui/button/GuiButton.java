package de.hglabor.bedwars.gui.button;

import de.hglabor.utils.noriskutils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;

public  class GuiButton {

    private Consumer<GuiButtonClickAction> onPress;
    private ItemStack itemStackResult;
    public void press(InventoryClickEvent event) {
        onPress.accept(new GuiButtonClickAction((Player) event.getWhoClicked(), this, event));
    }

    public GuiButton(String name, String description, Material icon, Consumer<GuiButtonClickAction> onPress) {
        this.onPress = onPress;
        this.itemStackResult = new ItemBuilder(icon).setName(name.replace("&", "§")).setDescription(description.replace("&", "§").split("##")).build();
    }

    public ItemStack getItemStackResult() {
        return itemStackResult;
    }
}
