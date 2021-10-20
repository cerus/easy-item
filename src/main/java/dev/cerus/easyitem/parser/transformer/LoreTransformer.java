package dev.cerus.easyitem.parser.transformer;

import dev.cerus.easyitem.exception.ParserException;
import dev.cerus.easyitem.parser.Parser;
import dev.cerus.easyitem.parser.Transformer;
import dev.cerus.easyitem.tokenizer.Token;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LoreTransformer implements Transformer {

    @Override
    public void transform(final ItemStack itemStack, final Parser parser) throws ParserException {
        parser.consume();
        parser.consume();

        final Token<?> token = parser.consume();
        if (token.getType() != Token.Type.STRING) {
            throw new ParserException("Not a string");
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        final List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', token.getValueUnsafe()));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public boolean matches(final Parser parser) {
        return parser.has(0)
                && parser.peek().equals(Token.Type.WORD, "with")
                && parser.has(1)
                && parser.peekNext().equals(Token.Type.WORD, "lore");
    }

}
