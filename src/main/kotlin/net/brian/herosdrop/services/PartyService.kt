package net.brian.herosdrop.services

import de.simonsator.partyandfriendsgui.api.datarequest.party.PartyDataCallBackRunnable
import de.simonsator.partyandfriendsgui.api.datarequest.party.PartyDataRequestCallbackAPI
import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.service.spec.server.SchedulerService
import net.brian.herosdrop.HeroesDrop
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timerTask

@Component
class PartyService (
    private val schedulerService: SchedulerService
):LifeCycleHook{

    val partyMap: HashMap<UUID,Int> = HashMap();

    override fun onEnable() {
        schedulerService.timer(30000).subscribe {
            Bukkit.getServer().onlinePlayers.forEach { player ->
                PartyDataRequestCallbackAPI.getInstance()
                    .fetchPartyData(player,  fun(pPlayer, partyData, i){
                        if(partyData.DOES_EXIST){
                            partyMap[player.uniqueId] = i;
                        }
                        else partyMap.remove(player.uniqueId);
                    })
            }
        }
    }

    fun isSameParty(player1: Player,player2: Player):Boolean{
        val id1 = partyMap[player1.uniqueId]
        val id2 = partyMap[player2.uniqueId]
        if(id1 != null && id2 != null){
            return id1 == id2;
        }
        return false;
    }

}