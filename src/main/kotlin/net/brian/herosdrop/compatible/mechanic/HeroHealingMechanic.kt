package net.brian.herosdrop.compatible.mechanic

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity
import io.lumine.xikage.mythicmobs.io.MythicLineConfig
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill
import io.lumine.xikage.mythicmobs.skills.SkillMechanic
import io.lumine.xikage.mythicmobs.skills.SkillMetadata
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderDouble
import net.brian.herosdrop.configs.SettingsConfig
import net.brian.herosdrop.services.MobService
import org.bukkit.entity.Damageable
import org.bukkit.entity.Player
import kotlin.math.min

class HeroHealingMechanic(skill:String, mlc: MythicLineConfig, private val mobService: MobService, private val settingsConfig: SettingsConfig): SkillMechanic(skill,mlc),ITargetedEntitySkill {

    private var placeholderAmount:PlaceholderDouble;
    private var usePercent: Boolean;

    private var combatRange: Double;

    init {
        placeholderAmount = mlc.getPlaceholderDouble("amount","0");
        usePercent = mlc.getBoolean("p",false);
        combatRange = settingsConfig.combatRange;
    }

    override fun castAtEntity(meta: SkillMetadata?, entity: AbstractEntity): Boolean {
        val caster = meta?.caster?.entity?.bukkitEntity;
        var heal = placeholderAmount.get(meta, entity);

        if(entity.isDamageable){
            if(usePercent){
                heal *= entity.maxHealth;
            }
            val damageable = (entity.bukkitEntity as Damageable)
            val resultHealth = min(damageable.health+heal,damageable.maxHealth)
            heal = resultHealth-damageable.health;
            damageable.health = resultHealth

            if(caster is Player){
                caster.getNearbyEntities(combatRange,combatRange,combatRange)
                    .map { mobService.getMobProfile(it.uniqueId) }
                    .forEach {
                        it?.addHealAmount(caster.uniqueId,heal)
                    }
            }
        }
        return true
    }
}