package com.example.login.user.service;

import com.example.login.common.enumType.ErrorCode;
import com.example.login.common.enumType.ResponseCode;
import com.example.login.common.exception.BusinessException;
import com.example.login.common.exception.CustomException;
import com.example.login.user.dto.UserDto;
import com.example.login.user.model.User;
import com.example.login.user.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{


    @Autowired
    PasswordEncoder passwordEncoder;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    @Override
    public User login(UserDto userDto) throws Exception{
        User user = userRepository.findById(userDto.getId()).orElseThrow(() ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        return user;
        //userRepository.login(userDto);
    }

    @Override
    public User signup(UserDto userDto) throws Exception{
        String encryptPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptPassword);
        return userRepository.save(userDto.toEntity());

    }
}
