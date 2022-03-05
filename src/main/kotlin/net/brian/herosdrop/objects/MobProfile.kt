package net.brian.herosdrop.objects

import net.brian.herosdrop.configs.MobConfig
import net.brian.herosdrop.configs.SettingsConfig
import net.brian.herosdrop.gui.BossResultGui
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.roundToInt


class MobProfile (
    val mobEntity: Entity,
    val mobConfig: MobConfig,
    val settingsConfig: SettingsConfig
){

    companion object{
        @JvmStatic
        val endGuiMaps :HashMap<String,BossResultGui> = HashMap()
    }


    private val playerProfiles :HashMap<UUID,PlayerProfile> = HashMap();


    var totalHeal = 0.0;var totalDamage = 0.0; var totalTank = 0.0


    fun addDealtDamage(uuid: UUID,damage: Double){
        totalDamage += damage;
        (playerProfiles[uuid]?:newPlayerProfile(uuid)).damage += damage;

    }

    fun addHealAmount(uuid: UUID, healAmount: Double){
        totalHeal += healAmount;
        (playerProfiles[uuid]?:newPlayerProfile(uuid)).heal += healAmount;
    }

    fun addTankedDamage(uuid: UUID,tankAmount: Double){
        totalTank+=tankAmount;
        (playerProfiles[uuid]?:newPlayerProfile(uuid)).tank += tankAmount;
    }

    fun end (lastHit: Player?){
        playerProfiles.forEach { (uuid, it) ->
            it.finalScore = settingsConfig.getScore(it.heal/totalHeal,it.damage/totalDamage,it.tank/totalTank)
            mobConfig.giveReward(Bukkit.getPlayer(uuid),it.finalScore)
        }

        if(mobConfig.isBoss){
            val sortedDamage = playerProfiles.toList().sortedBy { (_,p)->p.damage }.toList()
            val sortedHeal = playerProfiles.toList().sortedBy { (_,p)->p.heal }.toList()
            val sortedTank = playerProfiles.toList().sortedBy { (_,p)->p.tank }.toList()

            val sortedScore = playerProfiles.toList().sortedBy { (_,p)->p.damage }.toList()

            val message = settingsConfig.bossEndMessage

            for( i in message.indices){
                val damagePair = sortedDamage[0]
                val damagePercent = (damagePair.second.damage * 1000 / totalDamage).roundToInt() /10;
                message[i] = message[i].replace("{0}",Bukkit.getPlayer(damagePair.first)?.name ?:"從缺")
                message[i] = message[i].replace("{1}",damagePair.second.damage.toString())
                message[i] = message[i].replace("{2}",damagePercent.toString())
                val healPair = sortedHeal[0]
                val healPercent = (healPair.second.heal * 1000 / totalHeal).roundToInt() /10;
                message[i] = message[i].replace("{3}",Bukkit.getPlayer(healPair.first)?.name ?:"從缺")
                message[i] = message[i].replace("{4}",healPair.second.heal.toString())
                message[i] = message[i].replace("{5}",healPercent.toString())
                val tankPair = sortedTank[0]
                val tankPercent = (damagePair.second.tank * 1000 / totalTank).roundToInt() /10;
                message[i] = message[i].replace("{6}",Bukkit.getPlayer(tankPair.first)?.name ?:"從缺")
                message[i] = message[i].replace("{7}",tankPair.second.tank.toString())
                message[i] = message[i].replace("{8}",tankPercent.toString())
            }

            endGuiMaps[mobEntity.uniqueId.toString()] = BossResultGui(sortedScore,sortedDamage,sortedHeal,sortedTank)

            val component =
                TextComponent(*TextComponent.fromLegacyText(settingsConfig.checkMessage))
            component.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND,
            "HerosDrop result ${mobEntity.uniqueId}")
            playerProfiles.keys.map { Bukkit.getPlayer(it) }
                .forEach { it?.spigot()?.sendMessage(component) }

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