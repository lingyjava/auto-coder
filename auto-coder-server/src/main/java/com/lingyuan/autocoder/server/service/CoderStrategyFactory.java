package com.lingyuan.autocoder.server.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LingYuan
 */
public class CoderStrategyFactory {

    private static final Map<String, AutoCoderService> CODER_REGISTERS = new HashMap<>();

    /**
     * 由策略工厂注入 AutoCoderService 接口的不同实现类
     * @param code 实现类编码
     * @param impl 实现类
     */
    public static void register(String code, AutoCoderService impl) {
        if (code != null && !code.isEmpty()) {
            CODER_REGISTERS.put(code, impl);
        }
    }
    public static AutoCoderService get(String code) {
        return CODER_REGISTERS.get(code);
    }
}
