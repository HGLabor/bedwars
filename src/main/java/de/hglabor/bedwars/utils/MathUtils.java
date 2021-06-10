package de.hglabor.bedwars.utils;

public class MathUtils {

    //can be a feature in InventorBuilder itself
    public static int translateGuiScale(int toTranslate) {
        if(toTranslate < 10) {
            return 9;
        } else if(toTranslate > 10 && toTranslate <= 18) {
            return 18;
        } else if(toTranslate > 18 && toTranslate <= 27) {
            return 27;
        } else if(toTranslate > 27 && toTranslate <= 36) {
            return 36;
        } else if(toTranslate > 36 && toTranslate <= 45) {
            return 45;
        } else if(toTranslate > 45) {
            return 54;
        } else {
            return 9;
        }
    }

}
