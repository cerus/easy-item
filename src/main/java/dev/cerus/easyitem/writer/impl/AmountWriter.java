package dev.cerus.easyitem.writer.impl;

import dev.cerus.easyitem.writer.Writer;
import org.bukkit.inventory.ItemStack;

public class AmountWriter implements Writer {

    @Override
    public void write(final ItemStack stack, final StringBuilder buffer, final String separator) {
        buffer.append("with amount ").append(stack.getAmount());
    }

}
