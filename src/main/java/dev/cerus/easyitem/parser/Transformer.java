package dev.cerus.easyitem.parser;

import dev.cerus.easyitem.exception.ParserException;
import org.bukkit.inventory.ItemStack;

public interface Transformer {

    void transform(ItemStack itemStack, Parser parser) throws ParserException;

    default boolean matches(final Parser parser) {
        return true;
    }

}
