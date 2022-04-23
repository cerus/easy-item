package dev.cerus.easyitem.writer.impl;

import dev.cerus.easyitem.writer.Writer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullOwnerWriter implements Writer {

    @Override
    public void write(final ItemStack stack, final StringBuilder buffer, final String separator) {
        if (stack.hasItemMeta() && stack.getItemMeta() instanceof SkullMeta) {
            final SkullMeta meta = (SkullMeta) stack.getItemMeta();
            if (meta.getOwningPlayer() != null) {
                buffer.append("with owner \"" + meta.getOwningPlayer().getUniqueId() + "\"");
            }
        }
    }

}
