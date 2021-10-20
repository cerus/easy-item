package dev.cerus.easyitem.parser;

import dev.cerus.easyitem.Token;
import dev.cerus.easyitem.exception.ParserException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Parser {

    private final Set<Transformer> transformers = new HashSet<>();
    private final List<Token<?>> tokens;
    private int index;

    public Parser(final List<Token<?>> tokens) {
        this.tokens = tokens;
    }

    public ItemStack parse() throws ParserException {
        if (this.tokens.isEmpty()) {
            throw new ParserException("Empty");
        }

        final Token<?> token = this.consume();
        if (token.getType() != Token.Type.WORD || !((String) token.getValueUnsafe()).matches("(minecraft:)?[A-Za-z_0-9]+")) {
            throw new ParserException("Descriptor does not match an item");
        }

        final Material material = Material.matchMaterial(token.getValueUnsafe(), false);
        if (material == null) {
            throw new ParserException("Not an item type");
        }

        final ItemStack stack = new ItemStack(material);
        while (this.index < this.tokens.size()) {
            final int now = this.index;
            for (final Transformer transformer : this.transformers) {
                if (transformer.matches(this)) {
                    System.out.println("transforming with " + transformer.getClass().getSimpleName());
                    transformer.transform(stack, this);
                }
            }
            if (now == this.index) {
                this.consume();
            }
        }
        return stack;
    }

    public void addTransformers(final Transformer... transformers) {
        this.transformers.addAll(Arrays.asList(transformers));
    }

    public Token<?> peek() {
        return this.tokens.get(this.index);
    }

    public Token<?> peek(final int amt) {
        return this.tokens.get(this.index + amt);
    }

    public Token<?> peekNext() {
        return this.tokens.get(this.index + 1);
    }

    public Token<?> peekPrevious() {
        return this.tokens.get(this.index - 1);
    }

    public Token<?> consume() {
        return this.tokens.get(this.index++);
    }

    public boolean has(final int advance) {
        return this.index + advance < this.tokens.size();
    }

    public boolean has() {
        return this.index < this.tokens.size();
    }

}
