package com.example.userservice.service;

import com.example.userservice.domain.User;
import com.example.userservice.dto.SignupDto;
import com.example.userservice.dto.UsersDto;
import com.example.userservice.exception.CustomException;
import com.example.userservice.repository.UsersRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersDto signup(SignupDto signupDto) {
        validateDuplicateUserId(signupDto.getUserId());

        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        User user = User.create(signupDto.getUserId(), signupDto.getPassword(), signupDto.getName());
        usersRepository.save(user);
        return UsersDto.of(user);
    }

    private void validateDuplicateUserId(String userId) {
        if (usersRepository.existsByUserId(userId)) {
            throw new CustomException(HttpStatus.CONFLICT, "Duplicate User Id");
        }
    }

    public UsersDto getUser(String userId) {
        User user = usersRepository.findByUserId(userId)
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "User not found"));
        return UsersDto.of(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUserId(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(),
            true, true, true, true,
            Collections.emptyList()
        );
    }
}
