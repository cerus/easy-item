package dev.cerus.easyitem;

import dev.cerus.easyitem.exception.ParserException;
import dev.cerus.easyitem.parser.Parser;
import dev.cerus.easyitem.parser.ParserBuilder;
import dev.cerus.easyitem.tokenizer.Token;
import dev.cerus.easyitem.tokenizer.Tokenizer;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class EasyItem {

    private EasyItem() {
    }

    public static Parser newDefaultParser(final List<Token<?>> tokens) {
        return new ParserBuilder(tokens)
                .defaultTransformers()
                .build();
    }

    public static ItemStack parse(final String blob) throws ParserException {
        return newDefaultParser(new Tokenizer(blob).tokenize()).parse();
    }

    public static ItemStack unsafeParse(final String blob) {
        try {
            return parse(blob);
        } catch (final ParserException e) {
            e.printStackTrace();
            return null;
        }
    }

}
