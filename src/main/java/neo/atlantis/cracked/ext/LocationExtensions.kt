package neo.atlantis.cracked.ext

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.FallingBlock

fun Location.spawnFallingBlock(material: Material): FallingBlock? {
    return world?.spawnFallingBlock(this, material.createBlockData())
}

fun Location.spawnParticle(particle: Particle, count: Int) {
    this.world?.spawnParticle(particle, this, count, 0.0, 0.0, 0.0, 0.0)
}

fun Location.spawnParticle(particle: Particle, count: Int, dustOptions: Particle.DustOptions) {
    this.world?.spawnParticle(particle, this, count, dustOptions)
}

fun Location.playSound(sound: Sound, volume: Float, pitch: Float) {
    this.world?.playSound(this, sound, volume, pitch)
}