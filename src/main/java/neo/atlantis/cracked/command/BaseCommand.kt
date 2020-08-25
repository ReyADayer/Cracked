package neo.atlantis.cracked.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class BaseCommand : CommandExecutor {
    abstract fun onCommandByPlayer(player: Player, command: Command, label: String, args: CommandArgs): Boolean

    abstract fun onCommandByOther(sender: CommandSender, command: Command, label: String, args: CommandArgs): Boolean

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val commandArgs = CommandArgs(args)
        if (sender is Player) {
            return onCommandByPlayer(sender, command, label, commandArgs)
        }
        return onCommandByOther(sender, command, label, commandArgs)
    }
}