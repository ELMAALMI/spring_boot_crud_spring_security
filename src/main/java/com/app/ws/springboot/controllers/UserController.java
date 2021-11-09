package com.app.ws.springboot.controllers;

import com.app.ws.springboot.request.UserRequest;
import com.app.ws.springboot.response.UserResponse;
import com.app.ws.springboot.services.imp.UserServiceImp;
import com.app.ws.springboot.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImp userService;
    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto =  modelMapper.map(userRequest, UserDto.class);
        UserResponse response = modelMapper.map(userService.save(userDto), UserResponse.class);
        return new ResponseEntity<UserResponse>(response,HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id,@RequestBody UserRequest userRequest){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest,userDto);
        userDto = userService.update(userDto,id);
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(userDto,response);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        userService.delete(id);
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }
    @GetMapping(path = "{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(userService.getUserById(id),response);
        return new ResponseEntity(response,HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam("page") int page,@RequestParam("limit") int limit){
        List<UserResponse> users = new ArrayList<UserResponse>();
        List<UserDto> userList = userService.getUsers(limit,page);
        for(UserDto u:userList){
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(u,userResponse);
            users.add(userResponse);
        }
        return ResponseEntity.ok(users);
    }
}
