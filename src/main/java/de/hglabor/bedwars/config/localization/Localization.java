package de.hglabor.bedwars.config.localization;

import com.google.common.collect.ImmutableMap;
import de.hglabor.bedwars.Bedwars;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Localization {

    private static final HashMap<Locale, LocalizationFile> localizationFiles = new HashMap<>();

    private static final String colorValue = "\u00A7";
    private static final String colorKey = "&";
    private static final String newLineValue = "\n";
    private static final String newLineKey = "##";

    public static void init() {
        File localizationDirectory = new File(Bedwars.getPlugin().getDataFolder() + "/lang/");
        if (!localizationDirectory.exists()) {
            localizationDirectory.mkdirs();
        }
        for (File file : localizationDirectory.listFiles()) {
            if (file.getName().endsWith(".locale.yml")) {
                localizationFiles.put(Locale.getByFile(file), new LocalizationFile(file));
            }
        }
        // If no locale found it won't throw errors
        if (localizationFiles.isEmpty()) {
            localizationFiles.put(Locale.ENGLISH, new LocalizationFile(new File(localizationDirectory, "en.locale.yml")));
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
            return yamlConfiguration.getString(key).replaceAll(colorKey, colorValue).replaceAll(newLineKey, newLineValue);
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
            return result;
        }
        return key;
    }

}
