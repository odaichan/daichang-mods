package net.daichang.dcmods;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DCLogger {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // ANSI颜色代码
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String GRAY = "\u001B[90m";

    // 日志打印方法
    private void log(String level, String color, String message, Object... args) {
        String timestamp = dateFormat.format(new Date());
        String formattedMessage = String.format(message, args);
        System.out.printf("%s[%s][DC Mod][%s] %s%s%s%n", color, timestamp, level, formattedMessage, RESET, GRAY);
    }

    // INFO 日志
    public void info(String message, Object... args) {
        log("INFO", CYAN, message, args);
    }

    // WARN 日志
    public void warn(String message, Object... args) {
        log("WARN", YELLOW, message, args);
    }

    // ERROR 日志
    public void error(String message, Object... args) {
        log("ERROR", RED, message, args);
    }

    // SUCCESS 日志
    public void success(String message, Object... args) {
        log("SUCCESS", GREEN, message, args);
    }

    // DEBUG 日志
    public void debug(String message, Object... args) {
        log("DEBUG", BLUE, message, args);
    }

    // EXCEPTION 日志
    public void exception(Throwable throwable) {
        error("Exception occurred: %s", throwable.getMessage());
        throwable.printStackTrace(System.out);
    }
}
