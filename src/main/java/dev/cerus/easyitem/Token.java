package dev.cerus.easyitem;

public class Token<T> {

    private final Type type;
    private final T value;

    public Token(final Type type, final T value) {
        this.type = type;
        this.value = value;
    }

    public boolean equals(final Type type, final Object v) {
        return this.type == type && this.getValue().equals(v);
    }

    public T getValue() {
        return this.value;
    }

    public <R> R getValueUnsafe() {
        return (R) this.value;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        WORD(String.class),
        INTEGER(int.class),
        LONG(long.class),
        DOUBLE(double.class),
        STRING(String.class),
        NEWLINE(char.class);

        private final Class<?> backing;

        Type(final Class<?> backing) {
            this.backing = backing;
        }

        public Class<?> getBacking() {
            return this.backing;
        }

    }

}
