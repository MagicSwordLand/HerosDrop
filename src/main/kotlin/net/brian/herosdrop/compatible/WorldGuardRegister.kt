package net.brian.herosdrop.compatible

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.flags.StateFlag
import com.sk89q.worldguard.protection.regions.RegionContainer
import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.service.spec.server.EventService
import net.brian.herosdrop.services.PartyService
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent

@Component
class WorldGuardRegister(
    private val eventService: EventService,
    private val partyService: PartyService
) :LifeCycleHook{

    companion object{
        @JvmStatic
        val DamageOtherParties: StateFlag = StateFlag("damage-other-parties",false);
    }

    lateinit var regionContainer :RegionContainer;

    override fun onEnable() {
        regionContainer = WorldGuard.getInstance().platform.regionContainer;
    }


    fun canDamageOtherParties(location: Location): Boolean{
        return regionContainer.createQuery().getApplicableRegions(BukkitAdapter.adapt(location)).testState(null,
            DamageOtherParties)
    }

}