package xyz.srclab.spring.boot.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface TestMarker {

    Map<Object, Map<Object, Object>> markers = new ConcurrentHashMap<>();

    default void mark(Object key) {
        markers.computeIfAbsent(this, k -> new HashMap<>()).put(key, generateMark(key));
    }

    default void mark(Object key, Object value) {
        markers.computeIfAbsent(this, k -> new HashMap<>()).put(key, value);
    }

    default Object getActualMark(Object key) {
        return markers.computeIfAbsent(this, k -> new HashMap<>()).get(key);
    }

    default Object generateMark(Object key) {
        return "default - " + getClass();
    }

    default void printMarkInfo() {
        System.out.println(markers.computeIfAbsent(this, k -> new HashMap<>()));
    }

    default void printMarkInfo(PrintStream printStream) {
        printStream.println(markers.computeIfAbsent(this, k -> new HashMap<>()));
    }

    default void traceMarkInfo() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.trace(String.valueOf(markers.computeIfAbsent(this, k -> new HashMap<>())));
    }

    default void debugMarkInfo() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.debug(String.valueOf(markers.computeIfAbsent(this, k -> new HashMap<>())));
    }

    default void infoMarkInfo() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info(String.valueOf(markers.computeIfAbsent(this, k -> new HashMap<>())));
    }

    default void warnMarkInfo() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.warn(String.valueOf(markers.computeIfAbsent(this, k -> new HashMap<>())));
    }

    default void errorMarkInfo() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.error(String.valueOf(markers.computeIfAbsent(this, k -> new HashMap<>())));
    }
}
