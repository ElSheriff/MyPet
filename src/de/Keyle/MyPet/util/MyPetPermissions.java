/*
 * Copyright (C) 2011-2012 Keyle
 *
 * This file is part of MyPet
 *
 * MyPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyPet. If not, see <http://www.gnu.org/licenses/>.
 */

package de.Keyle.MyPet.util;

import de.Keyle.MyPet.MyPetPlugin;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class MyPetPermissions
{
    private static Object Permissions;

    public enum PermissionsType
    {
        NONE, Vault, Superperms
    }

    private static PermissionsType PermissionsMode = PermissionsType.NONE;


    public static boolean has(Player player, String node)
    {
        if (player.isOp())
        {
            //MyPetUtil.getLogger().info("--- permissions:" + node + " -> OP -> true");
            return true;
        }
        else if (PermissionsMode == PermissionsType.NONE)
        {
            //MyPetUtil.getLogger().info("--- permissions:" + node + " -> None -> true");
            return true;
        }
        else if (PermissionsMode == PermissionsType.Vault)
        {
            //MyPetUtil.getLogger().info("--- permissions:" + node + " -> Vault -> " + ((Permission) Permissions).has(player, node));
            return ((Permission) Permissions).has(player, node);
        }
        else if (PermissionsMode == PermissionsType.Superperms)
        {
            //MyPetUtil.getLogger().info("--- permissions:" + node + " -> Bukkit -> " + player.hasPermission(node));
            return player.hasPermission(node);
        }
        return false;

    }

    public static void setup(PermissionsType pt)
    {
        PermissionsMode = pt;
    }

    public static void setup()
    {
        Plugin p;

        p = MyPetPlugin.getPlugin().getServer().getPluginManager().getPlugin("Vault");
        if (p != null && PermissionsMode == PermissionsType.NONE)
        {
            PermissionsMode = PermissionsType.Vault;
            Permissions = null;

            RegisteredServiceProvider<Permission> permissionProvider = MyPetPlugin.getPlugin().getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
            if (permissionProvider != null)
            {
                Permissions = permissionProvider.getProvider();
            }
            if (Permissions != null)
            {
                MyPetUtil.getLogger().info("Vault integration enabled!");
                MyPetUtil.getDebugLogger().info("Permissions: Vault");
                return;
            }
            PermissionsMode = PermissionsType.NONE;
        }

        if (PermissionsMode == PermissionsType.NONE && MyPetConfig.Superperms)
        {
            PermissionsMode = PermissionsType.Superperms;
            MyPetUtil.getLogger().info("Superperms integration enabled!");
            MyPetUtil.getDebugLogger().info("Permissions: Superperms");
            return;
        }

        MyPetUtil.getLogger().info("No permissions system found!");
        MyPetUtil.getDebugLogger().info("Permissions: -");
    }

    public static PermissionsType getPermissionsMode()
    {
        return PermissionsMode;
    }
}