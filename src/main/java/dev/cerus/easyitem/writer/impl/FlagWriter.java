package dev.cerus.easyitem.writer.impl;

import dev.cerus.easyitem.writer.Writer;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class FlagWriter implements Writer {

    @Override
    public void write(final ItemStack stack, final StringBuilder buffer, final String separator) {
        if (stack.hasItemMeta()) {
            buffer.append(Arrays.stream(ItemFlag.values())
                    .filter(itemFlag -> stack.getItemMeta().hasItemFlag(itemFlag))
                    .map(itemFlag -> "with flag \"" + itemFlag.name() + "\"")
                    .collect(Collectors.joining(separator)));
        }
    }

}
