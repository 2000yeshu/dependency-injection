import com.google.inject.Guice;
import com.google.inject.Injector;
import modules.LoggingModule;
import modules.PropertiesModule;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Injector injector = Guice.createInjector(new PropertiesModule(), new LoggingModule());
        final TaskManager mymanager = injector.getInstance(TaskManager.class);
        mymanager.execute().get();
    }
}