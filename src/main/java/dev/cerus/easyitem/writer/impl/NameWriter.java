package dev.cerus.easyitem.writer.impl;

import dev.cerus.easyitem.writer.Writer;
import org.bukkit.inventory.ItemStack;

public class NameWriter implements Writer {

    @Override
    public void write(final ItemStack stack, final StringBuilder buffer, final String separator) {
        if (stack.hasItemMeta() && stack.getItemMeta().hasDisplayName()) {
            buffer.append("with name \"").append(this.escape(this.untranslate(stack.getItemMeta().getDisplayName()))).append("\"");
        }
    }

}
