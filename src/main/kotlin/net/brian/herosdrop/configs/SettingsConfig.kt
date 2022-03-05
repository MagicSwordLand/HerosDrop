package net.brian.herosdrop.configs

import net.objecthunter.exp4j.ExpressionBuilder
import org.bukkit.entity.Entity

class SettingsConfig {

    private var scoreString:String = "0.6*%heal% + %damage% + %tank%";
    var combatRange:Double = 10.0;

    var bossEndMessage:ArrayList<String> = arrayListOf(
        "傷害第一: 玩家{0} 造成傷害{1} {2}%",
        "治療第一: 玩家{3} 治療了{4} {5}%",
        "坦傷第一: 玩家{6} 坦了{7} {8}%"
    )
    var checkMessage = "點擊查看詳細結果"

    fun shouldCount(entity: Entity) :Boolean{
        return true;
    }


    fun getScore(heal :Double,damage :Double,tank :Double) :Int{
        var string = scoreString;
        string = string.replace("%heal%",heal.toInt().toString())
        string = string.replace("%tank%",tank.toInt().toString())
        return ExpressionBuilder(string.replace("%damage%",damage.toInt().toString())).build().evaluate().toInt()
    }
}