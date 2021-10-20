# easy-item

A small library for parsing ItemStacks from a human-readable format (1.16.5+, Java 11)

TODO: Maybe add serialization (item to human-readable string)?

## Examples

```yaml
my-cool-item: |-
  diamond_sword with name "Sword of doom"
  with enchantment "sharpness" 16
  with lore "&c&lIncredible sword"
  with lore "&7Made by easy-item"
my-other-cool-item: "minecraft:player_head with owner \"Cerus_\""
pants: |-
  leather_leggings with color "#00AFFE"
```

## Installation

https://jitpack.io/#cerus/easy-item

```xml

<project>
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.github.cerus</groupId>
            <artifactId>easy-item</artifactId>
            <version>Tag</version>
        </dependency>
    </dependencies>
</project>
```

## Available transformers

**Amount**\
`with amount INTEGER`\
`with amount 32`

**Enchantment**\
`with enchantment STRING INTEGER`\
`with enchantment "minecraft:sharpness" 2`

**Flag**\
`with flag STRING`\
`with flag "HIDE_ENCHANTS"`

**Leather-color**\
`with color/leathercolor STRING`\
`with color/leathercolor "#00AFFE"`\
`with color/leathercolor "255,255,255"`

**Skullowner**\
`with skullowner/owner STRING`\
`with skullowner/owner "Cerus_"`\
`with skullowner/owner "06f8c3cc-a3c5-4b48-bc6d-d3ee8963f2af"`

## Usage

Parsing an item:

```java
import dev.cerus.easyitem.EasyItem;
import dev.cerus.easyitem.tokenizer.Token;
import dev.cerus.easyitem.tokenizer.Tokenizer;
import dev.cerus.easyitem.exception.ParserException;
import dev.cerus.easyitem.parser.Parser;
import dev.cerus.easyitem.parser.ParserBuilder;
import java.util.List;

class MyClass {

    public ItemStack makeStackQuick(String str) {
        // You could also use EasyItem#parse(String) but that 
        // would require you to catch a ParserException
        return EasyItem.unsafeParse(str);
    }

    public ItemStack makeStack(String str) {
        try {
            List<Token<?>> tokens = new Tokenizer(str)
                    .tokenize();
            Parser parser = new ParserBuilder(tokens)
                    .defaultTransformers()
                    .transformers(/*Add additional transformers here*/)
                    .build();
            // EasyItem#newDefaultParser(List<Token<?>>) also 
            // returns this exact parser
            return parser.parse();
        } catch (ParserException ex) {
            ex.printStackTrace();
            System.err.println("Invalid item string");
            return null;
        }
    }

}
```

Creating your own transformer:

```java
import dev.cerus.easyitem.tokenizer.Token;
import dev.cerus.easyitem.exception.ParserException;
import dev.cerus.easyitem.parser.Parser;
import dev.cerus.easyitem.parser.Transformer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

class MyTransformer implements Transformer {

    @Override
    public void transform(ItemStack itemStack, Parser parser) throws ParserException {
        // Consume the two words "with" and "isodd"
        parser.consume();
        parser.consume();

        // Consume next token which should be our number
        Token<?> numToken = parser.consume();
        if (numToken.getType() != Token.Type.INTEGER
                && numToken.getType() != Token.Type.LONG) {
            throw new ParserException("Not a (valid) number");
        }

        // Get number and make odd/even bool
        long num = numToken.getValueUnsafe();
        boolean odd = (num & 1) == 1;

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(odd ? "Is odd" : "Is even");
        itemStack.setItemMeta(meta);
    }

    @Override
    public boolean matches(Parser parser) {
        // It's important to check if we do have tokens available before we peek
        return parser.has() // Is parser index < tokens?
                && parser.peek().equals(Token.Type.WORD, "with")
                && parser.has(1) // Is parser index + 1 < tokens?
                && parser.peekNext().equals(Token.Type.WORD, "isodd");
    }
}
```