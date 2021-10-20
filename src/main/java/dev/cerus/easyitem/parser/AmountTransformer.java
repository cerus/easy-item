package dev.cerus.easyitem.parser;

import dev.cerus.easyitem.Token;
import dev.cerus.easyitem.exception.ParserException;
import org.bukkit.inventory.ItemStack;

public class AmountTransformer implements Transformer {

    @Override
    public void transform(final ItemStack itemStack, final Parser parser) throws ParserException {
        parser.consume();
        parser.consume();

        final Token<?> token = parser.consume();
        if (token.getType() != Token.Type.INTEGER || (int) token.getValueUnsafe() <= 0) {
            throw new ParserException("Not a (valid) number");
        }

        itemStack.setAmount(parser.consume().getValueUnsafe());
    }

    @Override
    public boolean matches(final Parser parser) {
        return parser.has(0)
                && parser.peek().equals(Token.Type.WORD, "with")
                && parser.has(1)
                && parser.peekNext().equals(Token.Type.WORD, "amount");
    }

}
