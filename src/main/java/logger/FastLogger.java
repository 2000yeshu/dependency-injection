package logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class FastLogger implements Logger {
    private final String identifier;
    private final OutputStream stream;
    private final List<String> buffer;

    @Inject
    public FastLogger(@Named("logger.fast.identifier") final String identifier,
                      @Named("file-output-stream") final OutputStream stream) {
        this.identifier = identifier;
        this.stream = stream;
        this.buffer = new ArrayList<>();
    }

    public boolean write(final String word) {
        this.buffer.add(identifier + " " + word + "\n");
        return true;
    }

    public CompletableFuture<Void> flushAsync() {
        CompletableFuture<Void> result = CompletableFuture.completedFuture(null);
        final ExecutorService service =  Executors.newSingleThreadExecutor();
        for(final String word : this.buffer){
            result = result.thenAcceptAsync(__ -> {
                try {
                    this.stream.write(word.getBytes());
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, service);
        }
        return result;
    }

    public boolean close() {
        try {
            stream.flush();
            stream.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}