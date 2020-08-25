package neo.atlantis.cracked.ext

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound

fun Location.spawnParticle(particle: Particle, count: Int) {
    this.world?.spawnParticle(particle, this, count, 0.0, 0.0, 0.0, 0.0)
}

fun Location.playSound(sound: Sound, volume: Float, pitch: Float) {
    this.world?.playSound(this, sound, volume, pitch)
}