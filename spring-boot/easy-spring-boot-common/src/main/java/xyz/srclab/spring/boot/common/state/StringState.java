package xyz.srclab.spring.boot.common.state;

public interface StringState<T extends StringState<T>> extends State<String, String, T> {
}
