package dev.cerus.easyitem;

import dev.cerus.easyitem.exception.ParserException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private final List<Token<?>> tokens = new ArrayList<>();
    private final char[] chars;
    private int index;
    private StringBuffer buffer;

    public Tokenizer(final String blob) {
        this.chars = blob.toCharArray();
    }

    public List<Token<?>> tokenize() throws ParserException {
        while (this.index < this.chars.length) {
            final char c = this.peek();
            if (Character.isDigit(c) || (c == '-' && Character.isDigit(this.peekNext()))) {
                this.parseNumber();
            } else if (c == '"' && !this.isEscaped()) {
                this.parseString();
            } else if (c == '\n') {
                this.tokens.add(new Token<>(Token.Type.NEWLINE, this.consume()));
            } else if (!Character.isWhitespace(c)) {
                this.parseWord();
            } else {
                this.consume();
            }
        }
        return this.tokens;
    }

    private void parseWord() {
        this.buffer = new StringBuffer();
        while (this.index < this.chars.length && !Character.isWhitespace(this.peek())) {
            this.buffer.append(this.consume());
        }
        this.tokens.add(new Token<>(Token.Type.WORD, this.buffer.toString()));
    }

    private void parseString() throws ParserException {
        this.consume();

        this.buffer = new StringBuffer();
        while (this.peek() != '"' || this.isEscaped()) {
            if (this.index == this.chars.length - 1) {
                throw new ParserException("Unterminated string");
            }
            this.buffer.append(this.consume());
        }
        this.consume();
        this.tokens.add(new Token<>(Token.Type.STRING, this.buffer.toString()));
    }

    private void parseNumber() throws ParserException {
        boolean hadDecimal = false;
        boolean hadMinus = false;

        this.buffer = new StringBuffer();
        while (this.index < this.chars.length && (Character.isDigit(this.peek()) || this.peek() == '-' || this.peek() == '.')) {
            if (this.peek() == '-') {
                if (hadMinus) {
                    throw new ParserException("Double minus");
                }
                hadMinus = true;
            } else if (this.peek() == '.') {
                if (hadDecimal) {
                    throw new ParserException("Double decimal");
                }
                hadDecimal = true;
            }
            this.buffer.append(this.consume());
        }

        if (hadDecimal) {
            try {
                this.tokens.add(new Token<>(Token.Type.DOUBLE, Double.parseDouble(this.buffer.toString())));
            } catch (final NumberFormatException e) {
                throw new ParserException("Failed to parse double", e);
            }
        } else {
            try {
                this.tokens.add(new Token<>(Token.Type.INTEGER, Integer.parseInt(this.buffer.toString())));
            } catch (final NumberFormatException ignored) {
                try {
                    this.tokens.add(new Token<>(Token.Type.LONG, Long.parseLong(this.buffer.toString())));
                } catch (final NumberFormatException e) {
                    throw new ParserException("Failed to parse int/long", e);
                }
            }
        }
    }

    private char consume() {
        return this.chars[this.index++];
    }

    private char peek() {
        return this.chars[this.index];
    }

    private char peekNext() {
        return this.chars[this.index + 1];
    }

    private char peekPrev() {
        return this.chars[this.index - 1];
    }

    private boolean isEscaped() {
        if (this.index == 0) {
            return false;
        }
        return this.peekPrev() == '\\' && (this.index == 1 || this.chars[this.index - 2] != '\\');
    }

}
