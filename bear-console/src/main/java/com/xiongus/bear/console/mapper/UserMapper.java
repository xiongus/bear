package com.xiongus.bear.console.mapper;

import com.xiongus.bear.console.entity.dto.UserDTO;
import com.xiongus.bear.console.entity.po.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(source = "deleted", target = "enable")
  UserDTO convert(User user);
}
