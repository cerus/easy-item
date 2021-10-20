package dev.cerus.easyitem.parser;

import dev.cerus.easyitem.Token;
import dev.cerus.easyitem.exception.ParserException;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NameTransformer implements Transformer {

    @Override
    public void transform(final ItemStack itemStack, final Parser parser) throws ParserException {
        parser.consume();
        parser.consume();

        final Token<?> token = parser.consume();
        if (token.getType() != Token.Type.STRING) {
            throw new ParserException("Not a string");
        }

        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', token.getValueUnsafe()));
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public boolean matches(final Parser parser) {
        return parser.has(0)
                && parser.peek().equals(Token.Type.WORD, "with")
                && parser.has(1)
                && parser.peekNext().equals(Token.Type.WORD, "name");
    }

}
