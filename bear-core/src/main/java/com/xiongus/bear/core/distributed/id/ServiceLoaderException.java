package com.xiongus.bear.core.distributed.id;

/** service loader exception. */
public class ServiceLoaderException extends RuntimeException {

  private final Class<?> clazz;

  public ServiceLoaderException(Class<?> clazz, Exception caused) {
    super(String.format("Can not load class `%s`", clazz.getName()), caused);
    this.clazz = clazz;
  }

  public Class<?> getClazz() {
    return clazz;
  }
}
