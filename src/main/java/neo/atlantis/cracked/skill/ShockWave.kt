package neo.atlantis.cracked.skill

import io.reactivex.Observable
import neo.atlantis.cracked.ext.playSound
import neo.atlantis.cracked.ext.setBooleanMetadata
import neo.atlantis.cracked.ext.spawnFallingBlock
import neo.atlantis.cracked.ext.spawnParticle
import neo.atlantis.cracked.metadata.MetadataKeys
import neo.atlantis.cracked.range.Range
import neo.atlantis.cracked.range.RectRange
import neo.atlantis.cracked.util.LocationUtil
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

class ShockWave(private val entity: Entity, private val plugin: JavaPlugin) : Skill(entity, plugin) {
    private val radius = 1.0
    private val range = RectRange(0.5, 0.5, 0.5)

    override fun execute() {
        val location = entity.location
        location.playSound(Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 0.933f)
        Observable.interval(100, TimeUnit.SECONDS)
                .take(20)
                .doOnNext {
                    if(it % 10 == 0L){
                        impulse(location)
                    }
                }.subscribe()
    }

    private fun impulse(location: Location) {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(40)
                .doOnNext { data ->
                    val circleRadius = radius + data / 4.0
                    val count: Int = if (data < 8L) {
                        8
                    } else {
                        data.toInt()
                    }
                    repeat(count) {
                        drawCircle(location, circleRadius, 2.0 * it.toDouble() * Math.PI / count.toDouble())
                    }
                    drawAura(location, data)
                }.subscribe()
    }

    private fun drawCircle(location: Location, radius: Double, angle: Double) {
        object : BukkitRunnable() {
            override fun run() {
                val currentLocation = LocationUtil.convertToPolarCoordinates(location, radius, angle)
                val material = currentLocation.block.getRelative(BlockFace.DOWN).type
                currentLocation.spawnFallingBlock(material)?.apply {
                    velocity = Vector(0.0, 0.3, 0.0)
                    dropItem = false
                    setBooleanMetadata(plugin, MetadataKeys.IS_CRACKED_BLOCK, true)
                }
                effect(currentLocation, range)
            }
        }.runTaskLater(plugin, 1)
    }

    private fun drawAura(location: Location, data: Long) {
        val radius = data / 10
        val count = 3
        for (i in 0..count) {
            for (j in 0..count) {
                val angle1 = 2.0 * Math.PI * Random().nextInt(360).toDouble() / 360.0
                val angle2 = 2.0 * Math.PI * Random().nextInt(360).toDouble() / 360.0
                val x = radius * sin(angle1) * cos(angle2)
                val y = radius * sin(angle1) * sin(angle2)
                val z = radius * cos(angle1)
                val currentLocation = location.clone().add(x, y, z)
                if (data in 30..100) {
                    currentLocation.spawnParticle(Particle.SMOKE_NORMAL, 1)
                }
            }
        }
    }

    private fun effect(location: Location, range: Range) {
        object : BukkitRunnable() {
            override fun run() {
                range.getEntities(location).filter { it != entity }.filterIsInstance<LivingEntity>().forEach {
                    it.damage(100.0, entity)
                }
            }
        }.runTaskLater(plugin, 1)
    }
}