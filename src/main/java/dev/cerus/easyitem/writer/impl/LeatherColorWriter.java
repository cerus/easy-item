package dev.cerus.easyitem.writer.impl;

import dev.cerus.easyitem.writer.Writer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LeatherColorWriter implements Writer {

    @Override
    public void write(final ItemStack stack, final StringBuilder buffer, final String separator) {
        if (stack.hasItemMeta() && stack.getItemMeta() instanceof LeatherArmorMeta) {
            final LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            buffer.append("with color \"" + String.format("#%02x%02x%02x",
                    meta.getColor().getRed(),
                    meta.getColor().getGreen(),
                    meta.getColor().getBlue()) + "\"");
        }
    }

}
