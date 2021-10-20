package dev.cerus.easyitem.parser;

import dev.cerus.easyitem.Token;
import dev.cerus.easyitem.exception.ParserException;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantmentTransformer implements Transformer {

    @Override
    public void transform(final ItemStack itemStack, final Parser parser) throws ParserException {
        parser.consume();
        parser.consume();

        Token<?> token = parser.consume();
        if (token.getType() != Token.Type.STRING) {
            throw new ParserException("Not a string");
        }

        final String key = token.getValueUnsafe().toString().toLowerCase();
        final Enchantment enchantment = Enchantment.getByKey(key.startsWith("minecraft:")
                ? NamespacedKey.fromString(key) : NamespacedKey.minecraft(key));
        if (enchantment == null) {
            throw new ParserException("Unknown enchantment");
        }

        token = parser.consume();
        if (token.getType() != Token.Type.INTEGER) {
            throw new ParserException("Not a (valid) number");
        }

        final int level = token.getValueUnsafe();
        itemStack.addUnsafeEnchantment(enchantment, level);
    }

    @Override
    public boolean matches(final Parser parser) {
        return parser.has(0)
                && parser.peek().equals(Token.Type.WORD, "with")
                && parser.has(1)
                && parser.peekNext().equals(Token.Type.WORD, "enchantment");
    }

}
