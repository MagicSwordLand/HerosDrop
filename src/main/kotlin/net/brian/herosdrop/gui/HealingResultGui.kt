package net.brian.herosdrop.gui

import net.brian.herosdrop.HeroesDrop
import net.brian.herosdrop.objects.MobProfile
import net.brian.herosdrop.util.SkullBuilder
import net.brian.playerdatasync.gui.GuiElementGroup
import net.brian.playerdatasync.gui.InventoryGui
import net.brian.playerdatasync.gui.StaticGuiElement
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

class HealingResultGui(
    bossResultGui: BossResultGui,
    private val sortedMap: List<Pair<UUID, MobProfile.PlayerProfile>>
) :InventoryGui(HeroesDrop.instance,"Boss戰總治癒排名", arrayOf(
    "iiiiiiii",
    "iiiiiiii",
    "iiiiiiii",
    "iiiiiiii",
    "   abc  "
)){
    init {
        val groupElement = GuiElementGroup('i')
        var place = 1;
        sortedMap.forEach {
            groupElement.addElement(
                StaticGuiElement('i',
                    SkullBuilder.getPlayer(it.first),
                    "第 $place 名"+Bukkit.getOfflinePlayer(it.first).name,
                    "造成傷害: ${it.second.damage}",
                    "團隊回血: ${it.second.heal}",
                    "坦傷: ${it.second.tank}"
                ))
            place ++;
        }
        addElement('b', ItemStack(Material.YELLOW_CONCRETE),{
            bossResultGui.show(it.whoClicked)
            true
        },"總分排序")
        addElement('a', ItemStack(Material.YELLOW_CONCRETE),{
            bossResultGui.damageResultGui.show(it.whoClicked)
            true
        },"傷害排序")
        addElement('c', ItemStack(Material.YELLOW_CONCRETE),{
            bossResultGui.tankResultGui.show(it.whoClicked)
            true
        },"坦傷排序")
    }





}