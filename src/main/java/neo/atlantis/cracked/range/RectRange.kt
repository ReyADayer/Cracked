package neo.atlantis.cracked.range

import org.bukkit.Location
import org.bukkit.entity.Entity

class RectRange(private val x: Double, private val y: Double, private val z: Double) : Range() {
    override fun getEntities(location: Location): List<Entity> {
        return location.world?.getNearbyEntities(location, x, y, z)?.toList() ?: emptyList()
    }
}