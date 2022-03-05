package net.brian.herosdrop

import com.sk89q.worldguard.WorldGuard
import dev.reactant.reactant.core.ReactantPlugin
import net.brian.herosdrop.compatible.WorldGuardRegister
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.plugin.java.JavaPlugin

@ReactantPlugin(["net.brian.herosdrop"])
class HeroesDrop : JavaPlugin() {

    companion object {
        @JvmStatic
        val log: Logger = LogManager.getLogger("HerosDrop")
        @JvmStatic
        lateinit var instance: HeroesDrop
    }

    override fun onEnable() {
        instance = this
        WorldGuard.getInstance().flagRegistry.register(
            WorldGuardRegister.DamageOtherParties
        )
    }

}
