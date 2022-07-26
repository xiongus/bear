package com.xiongus.bear.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Json utils implement by Jackson.
 *
 * @author xiongus
 */
public class JacksonUtils {

  static ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  /**
   * Create a new empty Jackson {@link ObjectNode}.
   *
   * @return {@link ObjectNode}
   */
  public static ObjectNode createEmptyJsonNode() {
    return new ObjectNode(mapper.getNodeFactory());
  }
}
