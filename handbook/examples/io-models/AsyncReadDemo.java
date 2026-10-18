import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public final class AsyncReadDemo {
    public static void main(String[] args)
            throws IOException, InterruptedException, ExecutionException {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "Запуск: java AsyncReadDemo <future|callback> <файл>");
        }

        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(
                Path.of(args[1]), StandardOpenOption.READ)) {
            switch (args[0]) {
                case "future" -> readWithFutures(channel);
                case "callback" -> readWithCallback(channel);
                default -> throw new IllegalArgumentException(
                        "Режим должен быть future или callback");
            }
        }
    }

    private static void readWithFutures(AsynchronousFileChannel channel)
            throws InterruptedException, ExecutionException {
        ByteBuffer first = ByteBuffer.allocate(4);
        ByteBuffer second = ByteBuffer.allocate(4);
        Future<Integer> firstResult = channel.read(first, 0);
        Future<Integer> secondResult = channel.read(second, 4);
        System.out.println("Два вызова read вернули управление.");

        int firstCount = firstResult.get();
        System.out.println(describe(0, firstCount, first));
        int secondCount = secondResult.get();
        System.out.println(describe(4, secondCount, second));
    }

    private static void readWithCallback(AsynchronousFileChannel channel)
            throws InterruptedException, ExecutionException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        CompletableFuture<String> report = new CompletableFuture<>();
        channel.read(buffer, 0, buffer,
                new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer count, ByteBuffer data) {
                        try {
                            report.complete(describe(0, count, data));
                        } catch (RuntimeException failure) {
                            report.completeExceptionally(failure);
                        }
                    }

                    @Override
                    public void failed(Throwable failure, ByteBuffer data) {
                        report.completeExceptionally(failure);
                    }
                });
        System.out.println("Вызов read с обработчиком вернул управление.");
        System.out.println(report.get());
    }

    private static String describe(long offset, int count, ByteBuffer buffer) {
        StringBuilder text = new StringBuilder();
        text.append("Позиция ").append(offset)
                .append(", получено байтов: ").append(count)
                .append(", данные:");
        buffer.flip();
        while (buffer.hasRemaining()) {
            text.append(' ').append(String.format(
                    "%02X", Byte.toUnsignedInt(buffer.get())));
        }
        return text.toString();
    }
}
