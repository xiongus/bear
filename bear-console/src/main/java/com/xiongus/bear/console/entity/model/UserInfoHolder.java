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
public class UserInfoHolder {

  @Getter private final String userId;
  @Getter private final String username;
  @Getter private final String displayName;
  @Getter private final String email;
  @Getter private final String phoneNumber;
  @Getter private final String address;
  @Getter private final Set<String> authorities;

  private UserInfoHolder() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Jwt jwt = (Jwt) authentication.getPrincipal();
    JSONObject user = (JSONObject) jwt.getClaims().get("user");
    this.userId = user.getAsString("userId");
    this.username = user.getAsString("username");
    this.displayName = user.getAsString("displayName");
    this.email = user.getAsString("email");;
    this.phoneNumber = user.getAsString("phoneNumber");
    this.address = user.getAsString("address");

    this.authorities = Sets.newHashSet();
    JSONArray authorities = (JSONArray) jwt.getClaims().get("authorities");
    for (Object authority : authorities) {
      this.authorities.add(String.valueOf(authority));
    }
  }

  public static UserInfoHolder getInstance() {
    return new UserInfoHolder();
  }

  @Override
  public String toString() {
    return "UserInfo{" +
            "userId='" + userId + '\'' +
            ", username='" + username + '\'' +
            ", displayName='" + displayName + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", address='" + address + '\'' +
            ", authorities=" + authorities +
            '}';
  }
}
