package com.birthday.remembral.logger;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/*
* //https://github.com/orhanobut/logger
* apache license v 2.0
*
* */
public class LoggerImpl {

    private LoggerImpl() {
        //sonar
    }

    /*
    * This method clears LoggerAdapter if any and initializes Default AndroidLogAdapter
    *
    * @params
    * isLoggable -> true if you want to print package info logs in logcat, false otherwise
    * */
    public static void initializeAndroidLogAdapter(boolean isLoggable) {
        clearLoggerAdapter();

        Logger.addLogAdapter(new AndroidLogAdapter());

        isLoggable(isLoggable);
    }

    /*
    * This method clears LoggerAdapter if any and initializes AndroidLogAdapter with
    *       custom FormatStrategy
    * @params
    * showThreadInfo -> true if you want to show Thread info in logcat and false otherwise
    * isLoggable -> true if you want to print package info logs in logcat, false otherwise
    * */
    public static void initializeAndroidLogAdapterWithFormatStrategy(final boolean showThreadInfo,
                                                                     final boolean isLoggable,
                                                                     final String customTagForLogger) {
        clearLoggerAdapter();

        final FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(showThreadInfo) // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
                //.logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(customTagForLogger) // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        isLoggable(isLoggable);
    }


    /*
    * This method clears LoggerAdapter if any and initializes DiskLogAdapter with
    *       custom FormatStrategy
    *  creates a folder in sdcard as logger and ceates a file and appends logger Data
    * */
    public static void initializeAndroidLogAdapterWithDiskAdapter(final String customTagForLogger) {
        clearLoggerAdapter();

        final FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                .tag(customTagForLogger)
                .build();

        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
    }

    private static void clearLoggerAdapter() {
        Logger.clearLogAdapters();
    }

    private static void isLoggable(final boolean isLoggable) {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                if (isLoggable) {
                    return BuildConfig.DEBUG;
                } else {
                    return !BuildConfig.DEBUG;
                }
            }
        });
    }

    public static void loggerdebug(final String tag, final String message) {
        Logger.t(tag).d(message);
    }

    public static void loggerWarning(final String tag, final String message) {
        Logger.t(tag).w(message);
    }

    public static void loggerError(final String tag, final String message) {
        Logger.t(tag).e(message);
    }

    public static void loggerInfo(final String tag, final String message) {
        Logger.t(tag).i(message);
    }

    public static void loggerVerbose(final String tag, final String message) {
        Logger.t(tag).v(message);
    }

    public static void loggerJson(final String json) {
        Logger.json(json);
    }

    public static void loggerXml(final String xml) {
        Logger.xml(xml);
    }
}
