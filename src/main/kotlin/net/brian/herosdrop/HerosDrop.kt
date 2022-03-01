package net.brian.herosdrop

import dev.reactant.reactant.core.ReactantPlugin
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.plugin.java.JavaPlugin

@ReactantPlugin(["net.brian.herosdrop"])
class HerosDrop : JavaPlugin() {

    companion object {
        @JvmStatic
        val log: Logger = LogManager.getLogger("HerosDrop")
    }

}
