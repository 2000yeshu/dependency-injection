package logger;

import java.util.concurrent.CompletableFuture;

public interface Logger {
    boolean write(String s);

    CompletableFuture<Void> flushAsync();

    boolean close();


}