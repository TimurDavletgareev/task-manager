package com.effectivemobile.taskmanager.user.service;

import com.effectivemobile.taskmanager.error.exception.NotFoundException;
import com.effectivemobile.taskmanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        log.info("-- Возвращение UserDetails по имени пользователя:{}", name);

        UserDetails userDetails = userRepository.findByNameOrEmail(name, null)
                .orElseThrow(() -> new NotFoundException(String.format("- Пользователь с name=%s не найден", name)));

        log.info("-- UserDetails возвращен: {}", userDetails);

        return userDetails;
    }
}
