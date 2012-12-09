package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_Log;
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = ADMIN_LEVEL.OP, source = SOURCE_TYPE_ALLOWED.BOTH, ignore_permissions = false)
public class Command_tfbanlist extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase("purge"))
            {
                if (senderIsConsole || TFM_SuperadminList.isUserSuperadmin(sender))
                {
                    try
                    {
                        TFM_ServerInterface.wipeNameBans();
                        sender.sendMessage(ChatColor.GRAY + "Ban list has been purged.");
                    }
                    catch (Exception ex)
                    {
                        TFM_Log.severe(ex);
                    }

                    return true;
                }
                else
                {
                    sender.sendMessage(ChatColor.YELLOW + "You do not have permission to purge the ban list, you may only view it.");
                }
            }
        }

        StringBuilder banned_players = new StringBuilder();
        banned_players.append("Banned Players: ");
        boolean first = true;
        for (OfflinePlayer p : server.getBannedPlayers())
        {
            if (!first)
            {
                banned_players.append(", ");
            }
            first = false;
            banned_players.append(p.getName().trim());
        }

        sender.sendMessage(ChatColor.GRAY + banned_players.toString());

        return true;
    }
}
