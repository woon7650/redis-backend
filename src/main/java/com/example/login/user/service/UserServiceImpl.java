package com.example.login.user.service;

import com.example.login.common.exception.BusinessException;
import com.example.login.user.dto.UserDto;
import com.example.login.user.model.User;
import com.example.login.user.repository.UserRepository;
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

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    @Override
    public User login(UserDto userDto) throws Exception{
        User user = userRepository.findById(userDto.getId()).orElseThrow(() ->  new BusinessException("존재하지 않는 아이디"));

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            throw new BusinessException("비밀번호가 일치하지 않습니다");
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
