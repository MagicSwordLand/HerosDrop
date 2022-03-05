package net.brian.herosdrop.compatible.condition


import io.lumine.xikage.mythicmobs.adapters.AbstractEntity
import io.lumine.xikage.mythicmobs.skills.SkillCondition
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityComparisonCondition
import net.brian.herosdrop.services.DistinguishService

class AllyCondition(line: String, private val distinguishService: DistinguishService): SkillCondition(line) ,IEntityComparisonCondition{

    override fun check(caster: AbstractEntity, target: AbstractEntity): Boolean {
        return distinguishService.isAlly(caster.bukkitEntity,target.bukkitEntity)
    }

}