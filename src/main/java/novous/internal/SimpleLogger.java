package novous.internal;

import java.util.Arrays;

import novous.api.util.Logger;

/**
 * A simple implementation using Log4j2 of {@link Logger}.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class SimpleLogger implements Logger {

    private final org.apache.logging.log4j.Logger nativeLogger;

    public SimpleLogger(org.apache.logging.log4j.Logger logger) {
        this.nativeLogger = logger;
    }

    @Override
    public String getName() {
        return nativeLogger.getName();
    }

    @Override
    public void info(String... messages) {
        Arrays.asList(messages).forEach(nativeLogger::info);
    }

    @Override
    public void severe(String... messages) {
        Arrays.asList(messages).forEach(nativeLogger::error);
    }

    @Override
    public void warn(String... messages) {
        Arrays.asList(messages).forEach(nativeLogger::warn);
    }

}
