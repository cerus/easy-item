package dev.cerus.easyitem.parser;

import dev.cerus.easyitem.Token;
import java.util.List;

public class ParserBuilder {

    private final Parser parser;

    public ParserBuilder(final List<Token<?>> tokens) {
        this.parser = new Parser(tokens);
    }

    public ParserBuilder defaultTransformers() {
        return this.transformers(
                new AmountTransformer(),
                new EnchantmentTransformer(),
                new FlagTransformer(),
                new LeatherColorTransformer(),
                new LoreTransformer(),
                new NameTransformer(),
                new SkullOwnerTransformer(),
                new SkullTextureTransformer()
        );
    }

    public ParserBuilder transformers(final Transformer... transformers) {
        this.parser.addTransformers(transformers);
        return this;
    }

    public Parser build() {
        return this.parser;
    }

}
