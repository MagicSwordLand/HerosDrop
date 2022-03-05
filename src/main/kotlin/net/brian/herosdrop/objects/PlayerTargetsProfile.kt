package net.brian.herosdrop.objects

import net.brian.playerdatasync.PlayerDataSync
import net.brian.playerdatasync.data.PlayerData
import java.util.UUID

class PlayerTargetsProfile(uuid: UUID) :PlayerData(uuid){

    companion object{
        @JvmStatic
        val key = "playertargets"
        @JvmStatic
        fun get(uuid: UUID): PlayerTargetsProfile{
            return PlayerDataSync.getInstance().getData(key,uuid,PlayerTargetsProfile::class.java)
        }
    }


    var buffOtherTeams: Boolean = true;






}