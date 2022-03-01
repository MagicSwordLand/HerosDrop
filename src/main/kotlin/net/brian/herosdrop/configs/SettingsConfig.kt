package net.brian.herosdrop.configs

import net.objecthunter.exp4j.ExpressionBuilder
import org.bukkit.entity.Entity

class SettingsConfig {

    var scoreString:String = "%heal% + %damage% + %tank%";
    var combatRange:Double = 10.0;

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