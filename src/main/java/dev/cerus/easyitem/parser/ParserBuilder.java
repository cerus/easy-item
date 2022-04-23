package dev.cerus.easyitem.parser;

import dev.cerus.easyitem.parser.transformer.AmountTransformer;
import dev.cerus.easyitem.parser.transformer.EnchantmentTransformer;
import dev.cerus.easyitem.parser.transformer.FlagTransformer;
import dev.cerus.easyitem.parser.transformer.LeatherColorTransformer;
import dev.cerus.easyitem.parser.transformer.LoreTransformer;
import dev.cerus.easyitem.parser.transformer.NameTransformer;
import dev.cerus.easyitem.parser.transformer.SkullOwnerTransformer;
import dev.cerus.easyitem.tokenizer.Token;
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
                new SkullOwnerTransformer()
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
