package de.hglabor.bedwars.config.localization;

import com.google.common.collect.ImmutableMap;
import de.hglabor.bedwars.Bedwars;
import static de.hglabor.StringExtensionsKt.replaceHexCodes;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Localization {

    private static HashMap<Locale, LocalizationFile> localizationFiles = new HashMap<>();

    private static String colorValue = "\u00A7";
    private static String colorKey = "&";
    private static String newLineValue = "\n";
    private static String newLineKey = "##";

    public static void init() {
        File localizationDirecory = new File(Bedwars.getPlugin().getDataFolder() + "/lang/");
        if(!localizationDirecory.exists()) {
            localizationDirecory.mkdirs();
        }
        for (File file : localizationDirecory.listFiles()) {
            if(file.getName().endsWith(".locale.yml")) {
                localizationFiles.put(Locale.getByFile(file), new LocalizationFile(file));
            }
        }
    }

    public static String getMessage(String key, Locale locale) {
        YamlConfiguration yamlConfiguration = localizationFiles.get(locale).getConfiguration();
        if(!yamlConfiguration.contains(key)) {
            yamlConfiguration.set(key,key);
            try {
                yamlConfiguration.save(localizationFiles.get(locale).getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return replaceHexCodes(yamlConfiguration.getString(key).replaceAll(colorKey, colorValue).replaceAll(newLineKey, newLineValue));
        }
        return key;
    }

    public static String getMessage(String key, ImmutableMap<String,String> map, Locale locale) {
        YamlConfiguration yamlConfiguration = localizationFiles.get(locale).getConfiguration();
        if(!yamlConfiguration.contains(key)) {
            yamlConfiguration.set(key,key);
            try {
                yamlConfiguration.save(localizationFiles.get(locale).getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String result = yamlConfiguration.getString(key);
            for (String keys : map.keySet()) {
                result = result.replaceAll("\\$" + keys, map.get(keys));
            }
            result = result.replaceAll(colorKey, colorValue).replaceAll(newLineKey, newLineValue);
            return replaceHexCodes(result);
        }
        return key;
    }

}
