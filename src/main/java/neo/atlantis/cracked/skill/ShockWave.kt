package neo.atlantis.cracked.skill

import io.reactivex.Observable
import neo.atlantis.cracked.ext.playSound
import neo.atlantis.cracked.ext.spawnParticle
import neo.atlantis.cracked.range.Range
import neo.atlantis.cracked.range.RectRange
import neo.atlantis.cracked.util.LocationUtil
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

class ShockWave(private val entity: Entity, private val plugin: JavaPlugin) : Skill(entity, plugin) {
    private val radius = 1.0
    private val range = RectRange(0.5, 0.5, 0.5)

    override fun execute() {
        val location = entity.location
        Observable.interval(10, TimeUnit.MILLISECONDS)
                .take(120)
                .doOnNext {
                    val angle = 2 * Math.PI * it / 30
                    val circleRadius = radius + it / 60.0
                    drawCircle(location, circleRadius, angle)
                    drawCircle(location, circleRadius, angle + Math.PI / 2)
                    drawCircle(location, circleRadius, angle + 2 * Math.PI / 2)
                    drawCircle(location, circleRadius, angle + 3 * Math.PI / 2)
                    drawAura(location, it)
                }.subscribe()
    }

    private fun drawCircle(location: Location, radius: Double, angle: Double) {
        val currentLocation = LocationUtil.convertToPolarCoordinates(location, radius, angle)
        currentLocation.spawnParticle(Particle.SPELL_INSTANT, 10)
    }

    private fun drawAura(location: Location, data: Long) {
        val radius = data / 10
        if (data == 30.toLong()) {
            location.playSound(Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 0.933f)
        }

        val count = 3
        for (i in 0..count) {
            for (j in 0..count) {
                val angle1 = 2.0 * Math.PI * Random().nextInt(360).toDouble() / 360.0
                val angle2 = 2.0 * Math.PI * Random().nextInt(360).toDouble() / 360.0
                val x = radius * sin(angle1) * cos(angle2)
                val y = radius * sin(angle1) * sin(angle2)
                val z = radius * cos(angle1)
                val currentLocation = location.clone().add(x, y, z)
                effect(currentLocation, range)
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