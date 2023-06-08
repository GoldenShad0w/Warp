package goldenshadow.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {


    List<String> arguments = new ArrayList<>();

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        List<String> result = new ArrayList<>();
        if (s.equals("create_warp")) {
            if (args.length == 1) {
                return List.of("<name>");
            }
        } else {
            if (args.length == 1) {
                arguments = DataManager.getKeys();
                for (String a : arguments) {
                    if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                        result.add(a);
                }
                return result;
            }

            if (args.length == 2) {
                return List.of("<new_name>");
            }

        }
        if (args.length > 2) {
            arguments.clear();
            return arguments;
        }
        return null;
    }
}
