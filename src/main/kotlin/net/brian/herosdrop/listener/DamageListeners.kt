package net.brian.herosdrop.listener

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.core.dependency.injection.Inject
import dev.reactant.reactant.extra.config.type.SharedConfig
import dev.reactant.reactant.service.spec.config.Config
import dev.reactant.reactant.service.spec.server.EventService
import net.brian.herosdrop.configs.SettingsConfig
import net.brian.herosdrop.objects.MobProfile
import net.brian.herosdrop.services.MobService
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent

@Component
class DamageListeners(
    @Inject("plugins/HeroesDrop/settings.yml")
    private val settingsConfig: SharedConfig<SettingsConfig>,
    private val eventService: EventService,
    private val mobService: MobService
    ) : LifeCycleHook{


    override fun onEnable() {
        eventService{
            EntityDamageByEntityEvent::class
                .observable(true,EventPriority.HIGHEST)
                .filter {settingsConfig.content.shouldCount(it.entity) && it.damager is Player}
                .subscribe {
                    val mobProfile = mobService.getMobProfile(it.entity.uniqueId)
                    mobProfile?.addDealtDamage(it.damager.uniqueId, it.finalDamage)
                }

            EntityDamageByEntityEvent::class
                .observable(true,EventPriority.LOWEST)
                .filter {settingsConfig.content.shouldCount(it.damager) && it.entity is Player}
                .subscribe {
                    val mobProfile = mobService.getMobProfile(it.damager.uniqueId)
                    mobProfile?.addTankedDamage(it.entity.uniqueId,it.finalDamage)
                }
        }
    }


}