package neo.atlantis.cracked

import neo.atlantis.cracked.command.CrackCommand
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.annotation.command.Command
import org.bukkit.plugin.java.annotation.command.Commands
import org.bukkit.plugin.java.annotation.plugin.ApiVersion
import org.bukkit.plugin.java.annotation.plugin.Plugin
import org.bukkit.plugin.java.annotation.plugin.author.Author

@Plugin(name = "Cracked", version = "0.0.1")
@Author("ReyADayer")
@ApiVersion(ApiVersion.Target.DEFAULT)
@Commands(
        Command(
                name = PluginCommands.CRACK,
                desc = "crack command",
                usage = "/<command>"
        )
)
class Cracked : JavaPlugin() {
    override fun onEnable() {
        getCommand(PluginCommands.CRACK)?.setExecutor(CrackCommand(this))
    }

    override fun onDisable() {
    }
}