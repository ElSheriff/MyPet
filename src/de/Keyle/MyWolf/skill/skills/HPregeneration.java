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

package de.Keyle.MyWolf.skill.skills;

import de.Keyle.MyWolf.entity.types.MyPet.PetState;
import de.Keyle.MyWolf.skill.MyWolfGenericSkill;
import de.Keyle.MyWolf.util.MyWolfLanguage;
import de.Keyle.MyWolf.util.MyWolfUtil;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class HPregeneration extends MyWolfGenericSkill
{
    public static int HealtregenTime = 60;
    private int timeCounter = HealtregenTime - Level;

    public HPregeneration()
    {
        super("HPregeneration");
    }

    @Override
    public void upgrade()
    {
        Level++;
        MWolf.sendMessageToOwner(MyWolfUtil.setColors(MyWolfLanguage.getString("Msg_AddHPregeneration")).replace("%wolfname%", MWolf.Name).replace("%sec%", "" + (HealtregenTime - Level)));
    }

    public void schedule()
    {
        if (Level > 0 && MWolf.Status == PetState.Here)
        {
            timeCounter--;
            if (timeCounter <= 0)
            {
                MWolf.Wolf.getHandle().heal(1, EntityRegainHealthEvent.RegainReason.REGEN);
                timeCounter = HealtregenTime - Level;
            }
        }
    }
}