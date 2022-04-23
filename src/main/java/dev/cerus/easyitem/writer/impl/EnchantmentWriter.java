package dev.cerus.easyitem.writer.impl;

import dev.cerus.easyitem.writer.Writer;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemStack;

public class EnchantmentWriter implements Writer {

    @Override
    public void write(final ItemStack stack, final StringBuilder buffer, final String separator) {
        if (stack.hasItemMeta() && stack.getItemMeta().hasEnchants()) {
            buffer.append(stack.getEnchantments().entrySet().stream()
                    .map(e -> "with enchantment \"" + e.getKey().getKey() + "\" " + e.getValue())
                    .collect(Collectors.joining(separator)));
        }
    }

}
