package com.sciencefl.flynn.csleetcode;

public class SnowflakeIdGenerator {
    // 起始时间戳（可根据需求调整，如2020-01-01）
    private final static long START_STAMP = 1577808000000L;

    // 各部分的位长度
    private final static long SEQUENCE_BIT = 12;   // 序列号占12位
    private final static long WORKER_BIT = 10;     // 工作节点占10位
    private final static long MAX_WORKER_NUM = ~(-1L << WORKER_BIT); // 最大工作节点数（1023）
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT); // 最大序列号（4095）

    private final long workerId;     // 工作节点ID
    private long sequence = 0L;      // 序列号
    private long lastStamp = -1L;    // 上一次时间戳

    public SnowflakeIdGenerator(long workerId) {
        if (workerId > MAX_WORKER_NUM || workerId < 0) {
            throw new IllegalArgumentException("Worker ID 超出范围");
        }
        this.workerId = workerId;
    }

    // 生成下一个ID（线程安全）
    public synchronized long nextId() {
        long currentStamp = getCurrentStamp();
        if (currentStamp < lastStamp) {
            throw new RuntimeException("时钟回拨，拒绝生成ID");
        }
        System.out.println(MAX_SEQUENCE);
        if (currentStamp == lastStamp) {
            // 同一毫秒内序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) { // 当前毫秒序列号用完，等待下一毫秒
                currentStamp = waitNextMillis(lastStamp);
            }
        } else {
            sequence = 0L; // 新毫秒重置序列号
        }

        lastStamp = currentStamp;
        System.out.println("currentTime = " + (currentStamp - START_STAMP));
        System.out.println("lastStamp = " + lastStamp);
        System.out.println("sequence = " + sequence);
        System.out.println("workerId = " + workerId);

        // 拼接各部分生成最终ID
        return ((currentStamp - START_STAMP) << (WORKER_BIT + SEQUENCE_BIT))
                | (workerId << SEQUENCE_BIT)
                | sequence;
    }

    // 获取当前时间戳（毫秒）
    private long getCurrentStamp() {
        return System.currentTimeMillis();
    }

    // 阻塞等待到下一毫秒
    private long waitNextMillis(long lastStamp) {
        long currentStamp = getCurrentStamp();
        while (currentStamp <= lastStamp) {
            currentStamp = getCurrentStamp();
        }
        return currentStamp;
    }

    public static void main(String[] args) {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1); // 假设工作节点ID=1
        for (int i = 0; i < 10; i++) {
            long id = generator.nextId();
            System.out.println("生成的ID: " + id);
        }
    }
}
