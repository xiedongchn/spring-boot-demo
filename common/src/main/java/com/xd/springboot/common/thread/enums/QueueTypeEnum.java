package com.xd.springboot.common.thread.enums;

/**
 * 队列类型
 */
public enum QueueTypeEnum {

    LINKED_BLOCKING_QUEUE("LinkedBlockingQueue"),
    SYNCHRONOUS_QUEUE("SynchronousQueue"),
    ARRAY_BLOCKING_QUEUE("ArrayBlockingQueue"),
    DELAY_QUEUE("DelayQueue"),
    LINKED_TRANSFER_DEQUE("LinkedTransferQueue"),
    LINKED_BLOCKING_DEQUE("LinkedBlockingDeque"),
    PRIORITY_BLOCKING_QUEUE("PriorityBlockingQueue");

    QueueTypeEnum(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }

    public static boolean exists(String type) {
        for (QueueTypeEnum typeEnum : QueueTypeEnum.values()) {
            if (typeEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
