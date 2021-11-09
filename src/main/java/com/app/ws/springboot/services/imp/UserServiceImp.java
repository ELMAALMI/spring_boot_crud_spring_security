package com.app.ws.springboot.services.imp;

import com.app.ws.springboot.entities.User;
import com.app.ws.springboot.exceptions.UserException;
import com.app.ws.springboot.exceptions.errorMessage.UserErrorMessage;
import com.app.ws.springboot.repositories.UserRepository;
import com.app.ws.springboot.services.UserService;
import com.app.ws.springboot.shared.dto.AddressDto;
import com.app.ws.springboot.shared.dto.UserDto;
import com.app.ws.springboot.utils.CryptPassword;
import com.app.ws.springboot.utils.GenerateString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenerateString generateString;
    @Autowired
    private CryptPassword cryptPassword;
    @Override
    public UserDto save(@NonNull UserDto user) {
        if(userRepository.findUserByEmail(user.getEmail()) != null){
            throw new RuntimeException("email already used");
        }
        for(int i = 0 ; i < user.getAddresses().size() ; i++){
            AddressDto addressDto = new AddressDto();
            addressDto.setUser(user);
            user.getAddresses().set(i,addressDto);
        }
        ModelMapper modelMapper = new ModelMapper();

        User u = modelMapper.map(user,User.class);

        u.setEncryptedPassword(cryptPassword.Encode(user.getPassword()));
        u.setUserId(generateString.generateStringId(32));
        u = userRepository.save(u);

        return modelMapper.map(u,UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getEncryptedPassword(),new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(email);
        }
        UserDto u = new UserDto();
        BeanUtils.copyProperties(user,u);
        return u;
    }

    @Override
    public UserDto update(UserDto userDto, String userId) {
        User user = userRepository.findByUserId(userId);
        if(user==null){
            throw new RuntimeException("user not found with "+userId+" id");
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user = userRepository.save(user);
        UserDto u = new UserDto();
        BeanUtils.copyProperties(user,u);
        return u;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user==null){
            throw new UserException(UserErrorMessage.NOT_FOUND.getMessage());
        }
        UserDto u = new UserDto();
        BeanUtils.copyProperties(user,u);
        return u;
    }

    @Override
    public void delete(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user==null){
            throw new RuntimeException("user not found with "+userId+" id");
        }
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> usersDto = new ArrayList<>();
        Pageable p = PageRequest.of(page,limit);
        Page<User> userPage = userRepository.findAll(p);
        List<User> listUser =  userPage.getContent();
        for(User u : listUser){
            UserDto user = new UserDto();
            BeanUtils.copyProperties(u,user);
            usersDto.add(user);
        }
        return usersDto;
    }
}
