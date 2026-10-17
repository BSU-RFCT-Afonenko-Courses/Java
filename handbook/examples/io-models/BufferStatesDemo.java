import java.nio.ByteBuffer;

public final class BufferStatesDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        state("Создание", buffer);

        buffer.put((byte) 10).put((byte) 20).put((byte) 30);
        state("Три put", buffer);

        buffer.flip();
        state("flip", buffer);
        System.out.println("Получено: " + buffer.get() + " " + buffer.get());
        state("Два get", buffer);

        buffer.compact();
        state("compact", buffer);
        buffer.put((byte) 40).put((byte) 50);
        buffer.flip();
        state("Новая порция после сохранённого остатка", buffer);
        while (buffer.hasRemaining()) {
            System.out.println("Байт: " + buffer.get());
        }

        buffer.clear();
        state("clear", buffer);
        System.out.println("Сохранённое значение в ячейке 0: " + buffer.get(0));
        state("Абсолютный get(0)", buffer);
    }

    private static void state(String label, ByteBuffer buffer) {
        System.out.printf("%s: position=%d, limit=%d, capacity=%d%n",
                label, buffer.position(), buffer.limit(), buffer.capacity());
    }
}
