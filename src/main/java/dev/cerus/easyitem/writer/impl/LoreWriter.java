package dev.cerus.easyitem.writer.impl;

import dev.cerus.easyitem.writer.Writer;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemStack;

public class LoreWriter implements Writer {

    @Override
    public void write(final ItemStack stack, final StringBuilder buffer, final String separator) {
        if (stack.hasItemMeta() && stack.getItemMeta().hasLore()) {
            buffer.append(stack.getItemMeta().getLore().stream()
                    .map(s -> "with lore \"" + this.escape(this.untranslate(s)) + "\"")
                    .collect(Collectors.joining(separator)));
        }
    }

}
