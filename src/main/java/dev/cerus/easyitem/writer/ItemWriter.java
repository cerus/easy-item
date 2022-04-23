package dev.cerus.easyitem.writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class ItemWriter {

    private final List<Writer> writers = new ArrayList<>();

    public ItemWriter addWriters(final Writer... writers) {
        this.writers.addAll(Arrays.asList(writers));
        return this;
    }

    public String write(final ItemStack itemStack, final boolean multiline) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(itemStack.getType().getKey()).append(" ");
        for (final Writer writer : this.writers) {
            final int length = stringBuilder.length();
            writer.write(itemStack, stringBuilder, multiline ? "\n" : " ");
            if (length != stringBuilder.length()) {
                stringBuilder.append(multiline ? "\n" : "");
            }
        }
        return stringBuilder.toString().trim();
    }

}
