package com.xiongus.bear.core.distributed.id;

import com.xiongus.bear.core.utils.EnvUtil;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** SnowFlowerIdGenerator. */
public class SnowFlowerIdGenerator implements IdGenerator {

  private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

  /** Start time intercept (2018-08-05 08:34) */
  public static final long EPOCH = 1533429240000L;

  private static final Logger logger = LoggerFactory.getLogger(SnowFlowerIdGenerator.class);

  // the bits of sequence
  private static final long SEQUENCE_BITS = 12L;

  // the bits of workerId
  private static final long WORKER_ID_BITS = 10L;

  // the mask of sequence (111111111111B = 4095)
  private static final long SEQUENCE_MASK = 4095L;

  // the left shift bits of workerId equals 12 bits
  private static final long WORKER_ID_LEFT_SHIFT_BITS = 12L;

  // the left shift bits of timestamp equals 22 bits (WORKER_ID_LEFT_SHIFT_BITS + workerId)
  private static final long TIMESTAMP_LEFT_SHIFT_BITS = 22L;

  // the max of worker ID is 1024
  private static final long WORKER_ID_MAX_VALUE = 1024L;

  // CLOCK_REALTIME
  private final long startWallTime = System.currentTimeMillis();

  // CLOCK_MONOTONIC
  private final long monotonicStartTime = System.nanoTime();

  private long workerId;

  private long sequence;

  private long lastTime;

  private long currentId;

  {
    long workerId = EnvUtil.getProperty("bear.core.snowflake.worker-id", Integer.class, -1);

    if (workerId != -1) {
      this.workerId = workerId;
    } else {
      InetAddress address;
      try {
        address = InetAddress.getLocalHost();
      } catch (final UnknownHostException e) {
        throw new IllegalStateException(
            "Cannot get LocalHost InetAddress, please check your network!", e);
      }
      byte[] ipAddressByteArray = address.getAddress();
      this.workerId =
          (((ipAddressByteArray[ipAddressByteArray.length - 2] & 0B11) << Byte.SIZE)
              + (ipAddressByteArray[ipAddressByteArray.length - 1] & 0xFF));
    }
  }

  @Override
  public void init() {
    initialize(workerId);
  }

  @Override
  public long currentId() {
    return currentId;
  }

  @Override
  public long workerId() {
    return workerId;
  }

  @Override
  public synchronized long nextId() {
    long currentMillis = currentTimeMillis();
    if (this.lastTime == currentMillis) {
      if (0L == (this.sequence = ++this.sequence & 4095L)) {
        currentMillis = this.waitUntilNextTime(currentMillis);
      }
    } else {
      this.sequence = 0L;
    }

    this.lastTime = currentMillis;

    if (logger.isDebugEnabled()) {
      logger.debug(
          "{}-{}-{}",
          (new SimpleDateFormat(DATETIME_PATTERN)).format(new Date(this.lastTime)),
          workerId,
          this.sequence);
    }

    currentId = currentMillis - EPOCH << 22 | workerId << 12 | this.sequence;
    return currentId;
  }

  @Override
  public Map<Object, Object> info() {
    Map<Object, Object> info = new HashMap<>(4);
    info.put("currentId", currentId);
    info.put("workerId", workerId);
    return info;
  }

  // ==============================Constructors=====================================

  /**
   * init
   *
   * @param workerId worker id (0~1024)
   */
  public void initialize(long workerId) {
    if (workerId > WORKER_ID_MAX_VALUE || workerId < 0) {
      throw new IllegalArgumentException(
          String.format(
              "worker Id can't be greater than %d or less than 0, current workId %d",
              WORKER_ID_MAX_VALUE, workerId));
    }
    this.workerId = workerId;
  }

  /**
   * Block to the next millisecond until a new timestamp is obtained
   *
   * @param lastTimestamp The time intercept of the last ID generated
   * @return Current timestamp
   */
  private long waitUntilNextTime(long lastTimestamp) {
    long time;
    time = currentTimeMillis();
    while (time <= lastTimestamp) {
      time = currentTimeMillis();
    }

    return time;
  }

  private long currentTimeMillis() {
    return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - monotonicStartTime) + startWallTime;
  }
}
