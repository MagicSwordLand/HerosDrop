package net.brian.herosdrop.services

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.core.dependency.injection.Inject
import dev.reactant.reactant.extra.config.type.SharedConfig
import dev.reactant.reactant.service.spec.server.EventService
import net.brian.herosdrop.configs.SettingsConfig
import net.brian.herosdrop.objects.MobProfile
import org.bukkit.entity.Entity
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntitySpawnEvent
import java.util.UUID

@Component
class MobService (
    @Inject("plugins/HeroesDrop/settings.yml")
    private val settingsConfig: SharedConfig<SettingsConfig>,
    private val eventService: EventService
):LifeCycleHook{

    private val mobProfileMap : HashMap<UUID,MobProfile> = HashMap();

    fun getMobProfile(uuid: UUID) : MobProfile? {
        return mobProfileMap[uuid];
    }

    override fun onEnable() {
        eventService{
            EntitySpawnEvent::class.observable(true,EventPriority.MONITOR)
                .filter{settingsConfig.content.shouldCount(it.entity)}
                .map { it.entity }
                .subscribe { mobProfileMap[it.uniqueId] = MobProfile(it,"",settingsConfig.content) }

            EntityDeathEvent::class.observable(true,EventPriority.MONITOR)
                .subscribe {
                    mobProfileMap[it.entity.uniqueId]?.end(it.entity.killer)
                    mobProfileMap.remove(it.entity.uniqueId)
                }
        }
    }


}