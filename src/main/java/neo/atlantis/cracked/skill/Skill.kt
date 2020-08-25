package neo.atlantis.cracked.skill

import org.bukkit.entity.Entity
import org.bukkit.plugin.java.JavaPlugin

abstract class Skill(private val entity: Entity, private val plugin: JavaPlugin) {
    abstract fun execute()
}