package com.xiongus.bear.core.distributed.id;

import java.util.Map;

/**
 * IdGenerator.
 */
public interface IdGenerator {

  /** Perform the corresponding initialization operation. */
  void init();

  /**
   * current id info.
   *
   * @return current id
   */
  long currentId();

  /**
   * worker id info.
   *
   * @return worker id
   */
  long workerId();

  /**
   * Get next id.
   *
   * @return next id
   */
  long nextId();

  /**
   * Returns information for the current IDGenerator.
   *
   * @return {@link Map}
   */
  Map<Object, Object> info();
}
