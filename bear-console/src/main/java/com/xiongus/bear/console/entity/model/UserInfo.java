package com.xiongus.bear.console.entity.model;

import com.google.common.collect.Sets;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import java.util.Set;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

/** UserInfo. */
public class UserInfo {

  @Getter private final String id;
  @Getter private final String username;
  @Getter private final String displayName;
  @Getter private final String email;
  @Getter private final String mobileNumber;
  @Getter private final String address;
  @Getter private final Set<String> authorities;

  private UserInfo() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Jwt jwt = (Jwt) authentication.getPrincipal();
    JSONObject user = (JSONObject) jwt.getClaims().get("user");
    this.id = user.getAsString("id");
    this.username = user.getAsString("username");
    this.displayName = user.getAsString("displayName");
    this.email = user.getAsString("email");;
    this.mobileNumber = user.getAsString("mobileNumber");
    this.address = user.getAsString("address");

    this.authorities = Sets.newHashSet();
    JSONArray authorities = (JSONArray) jwt.getClaims().get("authorities");
    for (Object authority : authorities) {
      this.authorities.add(String.valueOf(authority));
    }
  }

  public static UserInfo getInstance() {
    return new UserInfo();
  }

  @Override
  public String toString() {
    return "UserInfo{" +
            "id='" + id + '\'' +
            ", username='" + username + '\'' +
            ", displayName='" + displayName + '\'' +
            ", email='" + email + '\'' +
            ", mobileNumber='" + mobileNumber + '\'' +
            ", address='" + address + '\'' +
            ", authorities=" + authorities +
            '}';
  }
}
