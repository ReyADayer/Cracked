package neo.atlantis.cracked.ext

import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.Metadatable
import org.bukkit.plugin.java.JavaPlugin

fun Metadatable.getBooleanMetadata(key: String): Boolean {
    return try {
        getMetadata(key)[0].value() as Boolean
    } catch (e: IndexOutOfBoundsException) {
        false
    }
}

fun Metadatable.setBooleanMetadata(plugin: JavaPlugin, key: String, value: Boolean) {
    setMetadata(key, FixedMetadataValue(plugin, value))
}