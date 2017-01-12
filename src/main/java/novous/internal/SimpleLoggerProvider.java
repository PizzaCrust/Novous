package novous.internal;

import com.google.common.collect.ImmutableList;

import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;

import novous.api.util.Logger;

/**
 * Represents a provider for {@link SimpleLogger} and implementation of {@link Logger.Provider}.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class SimpleLoggerProvider implements Logger.Provider {

    private final List<Logger> loggers = new ArrayList<>();

    @Override
    public Logger constructLogger(String loggerName) {
        SimpleLogger simpleLogger = new SimpleLogger(LogManager.getLogger(loggerName));
        loggers.add(simpleLogger);
        return simpleLogger;
    }

    @Override
    public ImmutableList<Logger> getConstructedLoggers() {
        return ImmutableList.copyOf(loggers);
    }

}
