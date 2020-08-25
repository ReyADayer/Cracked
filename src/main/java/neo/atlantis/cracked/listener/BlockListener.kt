package neo.atlantis.cracked.listener

import neo.atlantis.cracked.ext.getBooleanMetadata
import neo.atlantis.cracked.metadata.MetadataKeys
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityChangeBlockEvent

class BlockListener : Listener {
    @EventHandler
    fun onBlockFall(event: EntityChangeBlockEvent) {
        if (event.entityType == EntityType.FALLING_BLOCK) {
            val entity = event.entity
            val isCrackedBlock = entity.getBooleanMetadata(MetadataKeys.IS_CRACKED_BLOCK)
            if (isCrackedBlock) {
                event.isCancelled = true
            }
        }
    }
}