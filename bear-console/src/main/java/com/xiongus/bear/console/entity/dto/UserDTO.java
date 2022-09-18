package com.xiongus.bear.console.entity.dto;

import com.xiongus.bear.console.component.ValidPhoneNumber;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

  /** 用户账号 */
  @NotBlank(message = "{user.username}")
  private String username;

  /** 用户头像 */
  @NotBlank(message = "{user.avatarUrl}")
  private String avatarUrl;

  /** 用户名称 */
  @NotBlank(message = "{user.displayName}")
  private String displayName;

  /** 电子邮箱 */
  @Email(message = "{user.email}")
  private String email;

  /** 联系方式 */
  @ValidPhoneNumber(message = "{user.username}")
  private String phoneNumber;

  /** 联系地址 */
  private String address;

  /** 状态 */
  private boolean status;

  /** 是否验证 */
  private boolean isVerify;
}
