package de.hglabor.bedwars.utils;

public class MathUtils {

    //can be a feature in InventorBuilder itself
    public static int translateGuiScale(int toTranslate) {
        return toTranslate <= 9 ? 9 : toTranslate > 45 ? 54 : (toTranslate / 9) * 9;
    }

}
