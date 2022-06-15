package com.xiongus.bear.core.listener;

import com.xiongus.bear.common.utils.EnvUtil;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/** SpringApplicationRunListener. */
public class SpringApplicationRunListener
    implements org.springframework.boot.SpringApplicationRunListener, Ordered {

  private final SpringApplication application;

  private final String[] args;

  public SpringApplicationRunListener(SpringApplication application, String[] args) {
    this.application = application;
    this.args = args;
  }

  @Override
  public void environmentPrepared(
      ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
    injectEnvironment(environment);
  }

  private void injectEnvironment(ConfigurableEnvironment environment) {
    EnvUtil.setEnvironment(environment);
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }
}
