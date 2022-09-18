package com.xiongus.bear.compoment.config;

import java.util.List;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @link org.springframework.boot.autoconfigure.security.SecurityProperties;
 */
@Component
@ConfigurationProperties(prefix = "spring.security.ignore")
public class IgnoreUrlsConfig extends SecurityProperties {

  private List<String> urls;

  public String[] getUrls() {
    if (!CollectionUtils.isEmpty(urls)) {
      String[] array = new String[urls.size()];
      for (int i = 0; i < urls.size(); i++) {
        String url = urls.get(i);
        array[i] = url;
      }
      return array;
    }
    return new String[0];
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }
}
