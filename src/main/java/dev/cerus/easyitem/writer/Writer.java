package dev.cerus.easyitem.writer;

import org.bukkit.inventory.ItemStack;

public interface Writer {

    void write(ItemStack stack, StringBuilder buffer, String separator);

    default String escape(final String s) {
        final StringBuilder ret = new StringBuilder();
        char last = '\0';
        for (final char c : s.toCharArray()) {
            if (c == '"' && last != '\\') {
                ret.append("\\\"");
            } else {
                ret.append(c);
            }
            last = c;
        }
        return ret.toString();
    }

    default String untranslate(final String s) {
        final char[] b = s.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '§' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = '&';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

}
