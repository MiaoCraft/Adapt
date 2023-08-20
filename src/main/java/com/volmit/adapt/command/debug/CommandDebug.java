/*------------------------------------------------------------------------------
 -   Adapt is a Skill/Integration plugin  for Minecraft Bukkit Servers
 -   Copyright (c) 2022 Arcane Arts (Volmit Software)
 -
 -   This program is free software: you can redistribute it and/or modify
 -   it under the terms of the GNU General Public License as published by
 -   the Free Software Foundation, either version 3 of the License, or
 -   (at your option) any later version.
 -
 -   This program is distributed in the hope that it will be useful,
 -   but WITHOUT ANY WARRANTY; without even the implied warranty of
 -   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 -   GNU General Public License for more details.
 -
 -   You should have received a copy of the GNU General Public License
 -   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 -----------------------------------------------------------------------------*/

package com.volmit.adapt.command.debug;

import com.volmit.adapt.util.C;
import com.volmit.adapt.util.command.FConst;
import io.github.mqzn.commands.annotations.base.Default;
import io.github.mqzn.commands.annotations.base.ExecutionMeta;
import io.github.mqzn.commands.annotations.subcommands.SubCommandInfo;
import org.bukkit.command.CommandSender;

@SubCommandInfo(name = "debug", children = {CommandParticle.class, CommandPSP.class, CommandPAP.class, CommandSound.class, CommandVerbose.class})
@ExecutionMeta(permission = "adapt.idontknowwhatimdoingiswear", description = "This is the Debug locale")
public final class CommandDebug {

    @Default
    public void info(CommandSender sender) {
        FConst.success(" --- === " + C.GRAY + "[" + C.DARK_RED + "Adapt Debugger Help" + C.GRAY + "]: " + " === ---");
        FConst.info("/adapt debug (this command)").send(sender);
        FConst.info("/adapt debug verbose (toggle)").send(sender);
        FConst.info("/adapt debug particle <particle>").send(sender);
        FConst.info("/adapt debug sound <sound>").send(sender);
        FConst.info("/adapt debug psp (Print Skill Perm-nodes)").send(sender);
        FConst.info("/adapt debug pap (Print Skill Actions)").send(sender);
    }
}
