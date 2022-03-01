package net.brian.herosdrop.objects

import net.brian.herosdrop.configs.SettingsConfig
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.UUID

class MobProfile (val mobEntity: Entity,val mobType: String,val settingsConfig: SettingsConfig){

    private val playerProfiles :HashMap<UUID,PlayerProfile> = HashMap();

    private val finalScore: HashMap<UUID,Int> = HashMap();

    fun addDealtDamage(uuid: UUID,damage: Double){
        (playerProfiles[uuid]?:newPlayerProfile(uuid)).damage += damage;
    }

    fun addHealAmount(uuid: UUID, healAmount: Double){
        (playerProfiles[uuid]?:newPlayerProfile(uuid)).heal += healAmount;
    }

    fun addTankedDamage(uuid: UUID,tankAmount: Double){
        (playerProfiles[uuid]?:newPlayerProfile(uuid)).tank += tankAmount;
    }

    fun end (lastHit: Player?){
        playerProfiles.values.forEach {
            it.finalScore = settingsConfig.getScore(it.heal,it.damage,it.tank);
        }

    }

    private fun newPlayerProfile(uuid: UUID):PlayerProfile{
        val playerProfile = PlayerProfile();
        playerProfiles[uuid] = playerProfile;
        return playerProfile;
    }

    data class PlayerProfile(var damage: Double = 0.0,var heal: Double = 0.0,var tank: Double = 0.0){
        var finalScore: Int = 0;
    }
}