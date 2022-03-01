package net.brian.herosdrop.compatible

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.core.dependency.injection.Inject
import dev.reactant.reactant.extra.config.type.SharedConfig
import dev.reactant.reactant.service.spec.server.EventService
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent
import net.brian.herosdrop.compatible.mechanic.HeroHealingMechanic
import net.brian.herosdrop.configs.SettingsConfig
import net.brian.herosdrop.services.MobService

@Component
class MythicMobsRegister(
    val eventService: EventService,
    private val mobService: MobService,
    @Inject("plugins/HeroesDrop/settings.yml")
    private val settingsConfig: SharedConfig<SettingsConfig>
) :LifeCycleHook{

    override fun onEnable() {
        eventService{
            MythicMechanicLoadEvent::class.observable()
                .subscribe {
                    val name = it.mechanicName;
                    if(name.equals("HeroHeal",true)){
                        it.register(HeroHealingMechanic(it.mechanicName,it.config,mobService,settingsConfig.content))
                    }
                }
        }
    }
}