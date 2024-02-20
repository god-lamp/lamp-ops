package com.lamp.devops.fastmap;

/**
 * @author god-lamp
 * @since 2024-02-20
 * key过期回调函数
 */
@FunctionalInterface
public interface ExpireCallback<K, V> {

    /**
     * 当键过期时执行的函数
     *
     * @param key 过期的键
     * @param val 过期的值
     */
    void onExpire(K key, V val);
}
