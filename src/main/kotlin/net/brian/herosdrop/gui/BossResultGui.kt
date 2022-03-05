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
import java.util.UUID

class BossResultGui(
    sortedScore: List<Pair<UUID,MobProfile.PlayerProfile>>,
    sortedDamage: List<Pair<UUID,MobProfile.PlayerProfile>>,
    sortedHeal: List<Pair<UUID,MobProfile.PlayerProfile>>,
    sortedTank: List<Pair<UUID,MobProfile.PlayerProfile>>
) : InventoryGui(HeroesDrop.instance,"Boss戰總成績排名",
    arrayOf(
        "iiiiiiiii",
        "iiiiiiiii",
        "iiiiiiiii",
        "iiiiiiiii",
        "   abc"
    )) {

    var damageResultGui: DamageResultGui = DamageResultGui(this,sortedDamage)
    var tankResultGui = TankResultGui(this,sortedTank)
    var healingResultGui = HealingResultGui(this,sortedHeal)

    init {
        val groupElement = GuiElementGroup('i')
        var place = 1;
        sortedScore.forEach {
            groupElement.addElement(
                StaticGuiElement('i',
                    SkullBuilder.getPlayer(it.first),
                    "第 $place 名"+ Bukkit.getOfflinePlayer(it.first).name,
                    "造成傷害: ${it.second.damage}",
                    "團隊回血: ${it.second.heal}",
                    "坦傷: ${it.second.tank}"
                )
            )
            place ++;
        }
        addElement('a', ItemStack(Material.YELLOW_CONCRETE),{
            damageResultGui.show(it.whoClicked)
            true
        },"傷害排序")
        addElement('b', ItemStack(Material.YELLOW_CONCRETE),{
            tankResultGui.show(it.whoClicked)
            true
        },"坦傷排序")
        addElement('c', ItemStack(Material.YELLOW_CONCRETE),{
            healingResultGui.show(it.whoClicked)
            true
        },"治愈排序")
    }

}