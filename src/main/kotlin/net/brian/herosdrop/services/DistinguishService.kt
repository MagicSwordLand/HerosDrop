package net.brian.herosdrop.services

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import net.brian.herosdrop.compatible.WorldGuardRegister
import net.brian.herosdrop.objects.PlayerTargetsProfile
import net.brian.playerdatasync.PlayerDataSync
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

@Component
class DistinguishService(
    private val worldGuardRegister: WorldGuardRegister,
    private val partyService: PartyService
) :LifeCycleHook{

    override fun onEnable() {
        PlayerDataSync.getInstance().register(PlayerTargetsProfile.key,PlayerTargetsProfile::class.java)
    }


    fun isEnemy(entity1:Entity,entity2: Entity):Boolean{
        if(entity1 is Player && entity2 is Player){
            if(!partyService.isSameParty(entity1,entity2)){
                if(worldGuardRegister.canDamageOtherParties(entity1.location) &&
                    worldGuardRegister.canDamageOtherParties(entity2.location)){
                    return true
                }
            }
            return false
        }
        else{
            return true
        }
    }


    fun isAlly(caster:Entity,target: Entity):Boolean{
        if(caster is Player){
            val targetProfile = PlayerTargetsProfile.get(caster.uniqueId)
            if(target is Player){
                val sameParty = partyService.isSameParty(caster,target);
                if(sameParty){
                    return true
                }
                else{
                    if(targetProfile.buffOtherTeams){
                        return true
                    }
                }
            }
        }
        return false
    }


}