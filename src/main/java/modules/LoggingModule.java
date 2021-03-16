package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import logger.Logger;
import logger.SugaredLogger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class LoggingModule extends AbstractModule {

    public void configure() {
        bind(Logger.class).to(SugaredLogger.class);
    }

    @Provides
    @Named("console-output-stream")
    public OutputStream getConsoleOutputStream() {
        return System.out;
    }

    @Provides
    @Named("file-output-stream")
    public OutputStream getOutputStream(@Named("output-file.name") String fileLocation) throws FileNotFoundException {
        return new FileOutputStream(fileLocation);
    }


}
