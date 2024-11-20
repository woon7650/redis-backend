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
    private PasswordEncoder passwordEncoder;

    @Resource(name = "userRepository")
    private UserRepository userRepository;


    @Override
    public UserDto selectUserInfo(UserDto userDto) throws Exception{
        User user = userRepository.findById("ery950")
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        return UserDto.of(user);
    }

}
