package de.hglabor

import net.axay.kspigot.chat.input.awaitAnvilInput
import net.axay.kspigot.chat.input.awaitBookInputAsString
import net.axay.kspigot.chat.input.awaitChatInput
import org.bukkit.entity.Player

object InputUtils {

    fun awaitAnvilInput(player: Player, context: String): String {
        var returnStatement = "Invalid input"
        player.awaitAnvilInput(
            invTitle = context,
            startText = ""
        ) {
            returnStatement = it.input ?: "Invalid input"
        }
        return returnStatement
    }

    fun awaitChatInput(player: Player, context: String): String {
        var returnStatement = "Invalid input"
        player.awaitChatInput(
            question = context
        ) {
            returnStatement = it.input ?: "Invalid input"
        }
        return returnStatement
    }

    fun awaitBookInput(player: Player): String {
        var returnStatement = "Invalid input"
        player.awaitBookInputAsString {
            returnStatement = it.input ?: "Invalid input"
        }
        return returnStatement
    }

}