package dev.cerus.easyitem.parser.transformer;

import dev.cerus.easyitem.exception.ParserException;
import dev.cerus.easyitem.parser.Parser;
import dev.cerus.easyitem.parser.Transformer;
import dev.cerus.easyitem.tokenizer.Token;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullOwnerTransformer implements Transformer {

    @Override
    public void transform(final ItemStack itemStack, final Parser parser) throws ParserException {
        parser.consume();
        parser.consume();

        final Token<?> token = parser.consume();
        if (token.getType() != Token.Type.STRING) {
            throw new ParserException("Not a string");
        }

        final String str = token.getValueUnsafe();
        final SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        if (str.length() <= 16) {
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(str));
        } else {
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(str)));
        }
        itemStack.setItemMeta(meta);
    }

    @Override
    public boolean matches(final Parser parser) {
        return parser.has(0)
                && parser.peek().equals(Token.Type.WORD, "with")
                && parser.has(1)
                && (parser.peekNext().equals(Token.Type.WORD, "owner")
                || parser.peekNext().equals(Token.Type.WORD, "skullowner"));
    }

}
