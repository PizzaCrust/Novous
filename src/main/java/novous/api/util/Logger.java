package novous.api.util;

import com.google.common.collect.ImmutableList;

/**
 * Represents a logger.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public interface Logger {

    /**
     * Retrieves the name of the logger.
     * @return
     */
    String getName();

    /**
     * Logs a INFO level message.
     * @param messages
     */
    void info(String... messages);

    /**
     * Logs a SEVERE level message.
     * @param messages
     */
    void severe(String... messages);

    /**
     * Logs a WARN level message.
     * @param messages
     */
    void warn(String... messages);

    /**
     * Represents a provider to access/create loggers.
     *
     * @since 1.0-SNAPSHOT
     * @author PizzaCrust
     */
    interface Provider {

        /**
         * Avoids 'final' modifier in interface fields. Use INSTANCE[0] to access
         * the implementation instance. Implementations should set this field once they
         * have constructed a provider.
         */
        Provider[] INSTANCE = new Provider[1];

        /**
         * Retrieves a constructed logger by name.
         * @param loggerName
         * @return
         */
        default Logger getLogger(String loggerName) {
            final Logger[] targetLogger = {null};
            getConstructedLoggers().forEach((logger) -> {
                if (logger.getName().equals(loggerName)) {
                    targetLogger[0] = logger;
                }
            });
            return targetLogger[0];
        }

        /**
         * Constructs a logger and registers it.
         * @param loggerName
         * @return
         */
        Logger constructLogger(String loggerName);

        /**
         * Retrieves constructed loggers.
         * @return
         */
        ImmutableList<Logger> getConstructedLoggers();

        /**
         * Determines if the specified logger has been constructed. (only ones using
         * {@link #constructLogger(String)}.
         * @param name
         * @return
         */
        default boolean isLoggerConstructed(String name) {
            final boolean[] constructed = {false};
            getConstructedLoggers().forEach((logger) -> {
                if (logger.getName().equals(name)) {
                    constructed[0] = true;
                }
            });
            return constructed[0];
        }

    }

}
