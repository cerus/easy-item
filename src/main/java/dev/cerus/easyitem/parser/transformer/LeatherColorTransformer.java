package dev.cerus.easyitem.parser.transformer;

import dev.cerus.easyitem.exception.ParserException;
import dev.cerus.easyitem.parser.Parser;
import dev.cerus.easyitem.parser.Transformer;
import dev.cerus.easyitem.tokenizer.Token;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LeatherColorTransformer implements Transformer {

    @Override
    public void transform(final ItemStack itemStack, final Parser parser) throws ParserException {
        parser.consume();
        parser.consume();

        final Token<?> token = parser.consume();
        if (token.getType() != Token.Type.STRING) {
            throw new ParserException("Not a string");
        }

        final Color color;
        if (token.getValue().toString().matches("#[0-9A-Fa-f]{6}")) {
            color = Color.fromRGB(Integer.parseInt(token.getValue().toString().substring(1), 16));
        } else if (token.getValue().toString().matches("[0-9]+,(\\s+)?[0-9]+,(\\s+)?[0-9]+")) {
            final String[] split = token.getValue().toString().split(",(\\s+)?");
            color = Color.fromRGB(
                    Integer.parseInt(split[0]),
                    Integer.parseInt(split[1]),
                    Integer.parseInt(split[2])
            );
        } else {
            throw new ParserException("Not a color");
        }

        final LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
        meta.setColor(color);
        itemStack.setItemMeta(meta);
    }

    @Override
    public boolean matches(final Parser parser) {
        return parser.has(0)
                && parser.peek().equals(Token.Type.WORD, "with")
                && parser.has(1)
                && (parser.peekNext().equals(Token.Type.WORD, "color")
                || parser.peekNext().equals(Token.Type.WORD, "leathercolor"));
    }

}
