package com.xiongus.bear.core.listener;

import com.xiongus.bear.core.utils.EnvUtil;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/** SpringApplicationRunListener. */
public class SpringApplicationRunListener
    implements org.springframework.boot.SpringApplicationRunListener, Ordered {

  public SpringApplicationRunListener(SpringApplication application, String[] args) {
    super();
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
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
