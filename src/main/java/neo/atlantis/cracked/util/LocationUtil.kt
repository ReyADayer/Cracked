package neo.atlantis.cracked.util

import org.bukkit.Location
import kotlin.math.cos
import kotlin.math.sin

object LocationUtil {
    fun convertToPolarCoordinates(location: Location, radius: Double, angle: Double): Location {
        val currentLocation = location.clone()
        val x = cos(angle) * radius
        val y = 0.0
        val z = sin(angle) * radius
        return currentLocation.add(x, y, z)
    }
}