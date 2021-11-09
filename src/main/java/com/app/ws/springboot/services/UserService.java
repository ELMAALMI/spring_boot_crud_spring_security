package com.app.ws.springboot.services;

import com.app.ws.springboot.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto save(UserDto user);
    UserDto getUser(String email);
    UserDto update(UserDto userDto,String userId);
    UserDto getUserById(String userId);
    List<UserDto> getUsers(int page,int limit);
    void delete(String userId);
}
