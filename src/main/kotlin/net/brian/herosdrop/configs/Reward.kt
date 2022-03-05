package net.brian.herosdrop.configs

import net.Indyuce.mmoitems.MMOItems
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Reward {

    private var chance: Double = 0.5
    private var commands: MutableList<String> = arrayListOf()
    private var money: Float? = null
    private lateinit var mmoItem: MMOReward

    fun giveReward(player: Player?){
        if(player == null) return
        if(Math.random() < chance){
            val playerName = player.name
            if(commands.isNotEmpty()){
                commands.forEach {
                        command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%",playerName))
                }
            }
            if(money != null){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"money give $playerName $money")
            }
            if(this::mmoItem.isInitialized){
                val drop: ItemStack? = mmoItem.getItem();
                if(drop!= null){
                    if(player.inventory.addItem(drop).isEmpty()){
                        player.world.dropItem(player.location,drop) {
                            it.owner = player.uniqueId
                        }
                    }
                }
            }
        }
    }

    data class MMOReward(val type: String,val id:String){
        fun getItem(): ItemStack?{
            return MMOItems.plugin.getItem(type,id)
        }
    }


}