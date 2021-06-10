package de.hglabor

import net.md_5.bungee.api.ChatColor

fun replaceHexCodes(string: String): String {
    var finalString = string
    if(finalString.contains("#") && finalString.contains("&")) {
        repeat(finalString.toCharArray().size) {
            for ((currentIndex, char) in finalString.toCharArray().withIndex()) {
                if(char == '&') {
                    if(finalString.subSequence(currentIndex+1, currentIndex+2) == "#") {
                        if(finalString.length >= 8) {
                            val hexString = finalString.subSequence(currentIndex+2, currentIndex+8).toString()
                            finalString = finalString.replace("&#$hexString", ChatColor.of("#$hexString").toString())
                            continue
                        }
                    }
                }
            }
        }
    }
    return finalString
}