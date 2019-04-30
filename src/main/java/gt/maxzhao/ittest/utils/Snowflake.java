package gt.maxzhao.ittest.utils;

/**
 * @author maxzhao
 */
public class Snowflake {
    /**
     * 机器ID占用位数
     */
    private static final int WORK_ID_BIT_SIZE = 10;
    /**
     * 机器ID最大数
     */
    private static final int WORK_ID_MAX_VAL = ~(-1 << WORK_ID_BIT_SIZE);
    private int workId;
    /**
     * 序列占用最大位数
     */
    private static final int SEQUENCE_BIT_SIZE = 12;
    /**
     * 序列最大值(4098)
     */
    private static final int SEQUENCE_MAX_VAL = ~(-1 << SEQUENCE_BIT_SIZE);
    private int sequence = 0;
    private long lastTimestamp = 0L;
    /**
     * 最小日期1970-01-01
     */
    private static final long MIN_TIME = 1288834974657L;

    public Snowflake(int workId) {
        if (workId > WORK_ID_MAX_VAL || workId < 0) {
            throw new IllegalArgumentException("workId参数错误");
        }
        this.workId = workId;
    }

    public synchronized long nextId() {
        long currentTime = getTimestamp();
        if (lastTimestamp > currentTime) {
            throw new IllegalArgumentException("time clock is error!");
        }
        if (currentTime == lastTimestamp) {
            this.sequence = (this.sequence + 1) & SEQUENCE_MAX_VAL;
            if (sequence > SEQUENCE_MAX_VAL) {
                throw new IllegalArgumentException("并发量大，sequence溢出!");
            }
        } else {
            this.sequence = 0;
            lastTimestamp = currentTime;
        }

        return ((lastTimestamp - MIN_TIME) << (WORK_ID_BIT_SIZE+SEQUENCE_BIT_SIZE)) | (this.workId << WORK_ID_BIT_SIZE) | sequence;
    }

    private long getTimestamp() {
        return System.currentTimeMillis();
    }
}
