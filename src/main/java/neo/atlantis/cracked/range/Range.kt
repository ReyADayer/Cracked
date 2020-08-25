package neo.atlantis.cracked.range

import org.bukkit.Location
import org.bukkit.entity.Entity

abstract class Range {
    abstract fun getEntities(location: Location): List<Entity>
}