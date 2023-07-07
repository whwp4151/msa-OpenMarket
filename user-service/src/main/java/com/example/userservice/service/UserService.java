package com.example.userservice.service;

import com.example.userservice.domain.Users;
import com.example.userservice.dto.SignupDto;
import com.example.userservice.dto.UsersDto;
import com.example.userservice.exception.CustomException;
import com.example.userservice.repository.UsersRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersDto signup(SignupDto signupDto) {
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        Users users = Users.of(signupDto);
        usersRepository.save(users);
        return UsersDto.of(users);
    }

    public UsersDto getUser(String userId) {
        Users users = usersRepository.findByUserId(userId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        return UsersDto.of(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUserId(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(users.getUserId(), users.getPassword(),
            true, true, true, true,
            Collections.emptyList()
        );
    }
}
