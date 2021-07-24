package com.xd.springboot.common.thread.enums;

/**
 * 拒绝策略类型
 * @author xd
 */
public enum RejectedExecutionHandlerEnum {

    /**
     * 构造
     * @param type 策略
     */
    CALLER_RUNS_POLICY("CallerRunsPolicy"),
    ABORT_POLICY("AbortPolicy"),
    DISCARD_POLICY("DiscardPolicy"),
    DISCARD_OLDEST_POLICY("DiscardOldestPolicy");

    RejectedExecutionHandlerEnum(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }

    public static boolean exists(String type) {
        for (RejectedExecutionHandlerEnum typeEnum : RejectedExecutionHandlerEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
