package dev.cerus.easyitem;

import dev.cerus.easyitem.exception.ParserException;
import dev.cerus.easyitem.parser.Parser;
import dev.cerus.easyitem.parser.ParserBuilder;
import dev.cerus.easyitem.tokenizer.Token;
import dev.cerus.easyitem.tokenizer.Tokenizer;
import dev.cerus.easyitem.writer.ItemWriter;
import dev.cerus.easyitem.writer.impl.AmountWriter;
import dev.cerus.easyitem.writer.impl.EnchantmentWriter;
import dev.cerus.easyitem.writer.impl.FlagWriter;
import dev.cerus.easyitem.writer.impl.LeatherColorWriter;
import dev.cerus.easyitem.writer.impl.LoreWriter;
import dev.cerus.easyitem.writer.impl.NameWriter;
import dev.cerus.easyitem.writer.impl.SkullOwnerWriter;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class EasyItem {

    private static final ItemWriter DEFAULT_WRITER = new ItemWriter()
            .addWriters(
                    new AmountWriter(),
                    new NameWriter(),
                    new LoreWriter(),
                    new EnchantmentWriter(),
                    new FlagWriter(),
                    new LeatherColorWriter(),
                    new SkullOwnerWriter()
            );

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

    public static ItemWriter getDefaultWriter() {
        return DEFAULT_WRITER;
    }

    public static String write(final ItemStack itemStack, final boolean multiline) {
        return getDefaultWriter().write(itemStack, multiline);
    }

}
