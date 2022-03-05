package net.brian.herosdrop.commands;

import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import net.brian.herosdrop.HeroesDrop;
import net.brian.herosdrop.gui.BossResultGui;
import net.brian.herosdrop.objects.MobProfile;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Command implements CommandExecutor, LifeCycleHook {

    @Override
    public void onEnable(){
        HeroesDrop.getInstance().getCommand("HerosDrop").setExecutor(this);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length>=2){
            if(args[0].equalsIgnoreCase("result")){
                BossResultGui gui = MobProfile.getEndGuiMaps().get(args[1]);
                if(gui != null){
                    gui.show((HumanEntity) sender);
                }
            }

        }

        return true;
    }
}
