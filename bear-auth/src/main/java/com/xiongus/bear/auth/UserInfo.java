package com.xiongus.bear.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserInfo extends User {

  @Serial private static final long serialVersionUID = 5946986388047856568L;

  @Getter private final String id;
  @Getter private final String avatar;
  @Getter private final String displayName;
  @Getter private final String email;
  @Getter private final String mobileNumber;
  @Getter private final String address;

  @JsonCreator
  public UserInfo(
      @JsonProperty("username") String username,
      @JsonProperty("password") String password,
      @JsonProperty("enabled") boolean enabled,
      @JsonProperty("accountNonExpired") boolean accountNonExpired,
      @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired,
      @JsonProperty("accountNonLocked") boolean accountNonLocked,
      @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities,
      @JsonProperty("id") String id,
      @JsonProperty("avatar") String avatar,
      @JsonProperty("displayName") String displayName,
      @JsonProperty("email") String email,
      @JsonProperty("mobileNumber") String mobileNumber,
      @JsonProperty("address") String address) {
    super(
        username,
        password,
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        authorities);
    this.id = id;
    this.avatar = avatar;
    this.displayName = displayName;
    this.email = email;
    this.mobileNumber = mobileNumber;
    this.address = address;
  }
}
