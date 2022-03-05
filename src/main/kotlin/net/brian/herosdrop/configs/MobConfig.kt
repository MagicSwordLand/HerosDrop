package net.brian.herosdrop.configs

import org.bukkit.entity.Player

class MobConfig {

    private var type: String = ""
    var isBoss: Boolean = false
    private set

    private var rewards: HashMap<String,Reward> = HashMap()

    fun setRewards(){
        rewards.forEach { t, u ->
            val split = t.split("-")
            rewardsMap[Pair(split[0].toInt(),split[1].toInt())] = u
        }
    }

    @Transient
    var rewardsMap: HashMap<Pair<Int,Int>,Reward> = HashMap()

    fun giveReward(player: Player?,score: Int){
        rewardsMap.forEach{ entry ->
            val pair = entry.key
            if(score>=pair.first && score<=pair.second){
                entry.value.giveReward(player)
            }
        }
    }


}