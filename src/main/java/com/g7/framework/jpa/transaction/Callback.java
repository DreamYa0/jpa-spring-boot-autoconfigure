package com.g7.framework.jpa.transaction;

/**
 * 泛型加持。通用的事务业务逻辑。
 * @author dreamyao
 * @date 2021/11/14 下午20:43
 */
@FunctionalInterface
public interface Callback<T> {

    /**
     * 事务范围内的业务逻辑，带有返回值。
     * @return T
     * @throws Exception 未知异常
     */
    T execute() throws Exception;
}
