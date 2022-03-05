package net.brian.herosdrop.services

import com.sk89q.worldedit.bukkit.fastutil.Hash
import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.core.dependency.injection.Inject
import dev.reactant.reactant.extra.config.type.MultiConfigs
import dev.reactant.reactant.extra.config.type.SharedConfig
import dev.reactant.reactant.service.spec.server.EventService
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent
import net.brian.herosdrop.configs.MobConfig
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
    @Inject("plugins/HeroesDrop/drops")
    private val mobConfigs: MultiConfigs<MobConfig>,
    private val eventService: EventService
):LifeCycleHook{

    private val mobConfigsMap: HashMap<String,MobConfig> = HashMap()
    private val mobProfileMap: HashMap<UUID,MobProfile> = HashMap();

    fun getMobProfile(uuid: UUID) : MobProfile? {
        return mobProfileMap[uuid];
    }

    override fun onEnable() {
        reload()
        eventService{
            MythicMobSpawnEvent::class.observable(true,EventPriority.MONITOR)
                .filter{settingsConfig.content.shouldCount(it.entity)}
                .subscribe {
                    val mobConfig = mobConfigsMap[it.mobType.internalName]
                    if(mobConfig != null){
                        mobProfileMap[it.mob.uniqueId] = MobProfile(it.entity,mobConfig ,settingsConfig.content)
                    }
                }

            EntityDeathEvent::class.observable(true,EventPriority.MONITOR)
                .subscribe {
                    mobProfileMap[it.entity.uniqueId]?.end(it.entity.killer)
                    mobProfileMap.remove(it.entity.uniqueId)
                }
        }
    }

    fun reload(){
        mobConfigsMap.clear();

    }

}