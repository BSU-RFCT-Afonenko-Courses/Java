public interface ICharQ{void put(char c);char get();default void reset(){throw new UnsupportedOperationException("reset unsupported");}}
