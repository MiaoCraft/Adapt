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

package com.volmit.adapt.util.advancements.packet;

import com.volmit.adapt.util.advancements.JSONMessage;
import com.volmit.adapt.util.advancements.NameKey;
import com.volmit.adapt.util.advancements.advancement.Advancement;
import com.volmit.adapt.util.advancements.advancement.AdvancementDisplay;
import com.volmit.adapt.util.advancements.advancement.AdvancementFlag;
import com.volmit.adapt.util.advancements.advancement.ToastNotification;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;

import java.util.HashMap;

public class PacketConverter {

    private static final AdvancementRewards advancementRewards = new AdvancementRewards(0, new MinecraftKey[0], new MinecraftKey[0], null);

    private static final HashMap<NameKey, Float> smallestX = new HashMap<>();
    private static final HashMap<NameKey, Float> smallestY = new HashMap<>();

    public static void setSmallestX(NameKey tab, float smallestX) {
        PacketConverter.smallestX.put(tab, smallestX);
    }

    public static float getSmallestX(NameKey key) {
        return smallestX.containsKey(key) ? smallestX.get(key) : 0;
    }

    public static void setSmallestY(NameKey tab, float smallestY) {
        PacketConverter.smallestY.put(tab, smallestY);
    }

    public static float getSmallestY(NameKey key) {
        return smallestY.containsKey(key) ? smallestY.get(key) : 0;
    }

    public static float generateX(NameKey tab, float displayX) {
        return displayX - getSmallestX(tab);
    }

    public static float generateY(NameKey tab, float displayY) {
        return displayY - getSmallestY(tab);
    }

    /**
     * Creates an NMS Advancement
     *
     * @param advancement The Advancement to use as a base
     * @return The NMS Advancement
     */
    public static net.minecraft.advancements.Advancement toNmsAdvancement(Advancement advancement) {
        AdvancementDisplay display = advancement.getDisplay();

        ItemStack icon = CraftItemStack.asNMSCopy(display.getIcon());

        MinecraftKey backgroundTexture = null;
        boolean hasBackgroundTexture = display.getBackgroundTexture() != null;

        if (hasBackgroundTexture) {
            backgroundTexture = new MinecraftKey(display.getBackgroundTexture());
        }

        float x = generateX(advancement.getTab(), display.generateX());
        float y = generateY(advancement.getTab(), display.generateY());

        net.minecraft.advancements.AdvancementDisplay advDisplay = new net.minecraft.advancements.AdvancementDisplay(icon, display.getTitle().getBaseComponent(), display.getDescription().getBaseComponent(), backgroundTexture, display.getFrame().getNMS(), false, false, advancement.hasFlag(AdvancementFlag.SEND_WITH_HIDDEN_BOOLEAN));
        advDisplay.a(x, y);

        net.minecraft.advancements.Advancement parent = advancement.getParent() == null ? null : createDummy(advancement.getParent().getName());
        net.minecraft.advancements.Advancement adv = new net.minecraft.advancements.Advancement(
                advancement.getName().getMinecraftKey(),
                parent,
                advDisplay,
                advancementRewards,
                advancement.getCriteria().getCriteria(),
                advancement.getCriteria().getRequirements(),
                true);

        return adv;
    }

    /**
     * Creates an NMS Toast Advancement
     *
     * @param notification The Toast Notification to use as a base
     * @return The NMS Advancement
     */
    public static net.minecraft.advancements.Advancement toNmsToastAdvancement(ToastNotification notification) {
        ItemStack icon = CraftItemStack.asNMSCopy(notification.getIcon());

        MinecraftKey backgroundTexture = null;

        net.minecraft.advancements.AdvancementDisplay advDisplay = new net.minecraft.advancements.AdvancementDisplay(icon, notification.getMessage().getBaseComponent(), new JSONMessage(new TextComponent("Toast Notification")).getBaseComponent(), backgroundTexture, notification.getFrame().getNMS(), true, false, true);

        net.minecraft.advancements.Advancement adv = new net.minecraft.advancements.Advancement(ToastNotification.NOTIFICATION_NAME.getMinecraftKey(), null, advDisplay, advancementRewards, ToastNotification.NOTIFICATION_CRITERIA.getCriteria(), ToastNotification.NOTIFICATION_CRITERIA.getRequirements(), true);

        return adv;
    }

    /**
     * Creates a Dummy Advancement<br>Internally used to generate temporary parent advancements that need to be referenced in the packet
     *
     * @param name The name of the Advancement
     * @return the Dummy Advancement
     */
    public static net.minecraft.advancements.Advancement createDummy(NameKey name) {
//		net.minecraft.advancements.AdvancementDisplay advDisplay = new net.minecraft.advancements.AdvancementDisplay(null, null, null, null, AdvancementFrameType.a, false, false, false);
        net.minecraft.advancements.Advancement adv = new net.minecraft.advancements.Advancement(name.getMinecraftKey(), null, null, null, new HashMap<>(), new String[0][0], true);
        return adv;
    }

}