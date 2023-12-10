package com.effectivemobile.taskmanager.user.service;

import com.effectivemobile.taskmanager.error.exception.ConflictOnRequestException;
import com.effectivemobile.taskmanager.error.exception.NotFoundException;
import com.effectivemobile.taskmanager.user.dto.NewUserDto;
import com.effectivemobile.taskmanager.user.dto.UserFullDto;
import com.effectivemobile.taskmanager.user.dto.UserMapper;
import com.effectivemobile.taskmanager.user.model.User;
import com.effectivemobile.taskmanager.user.repository.UserRepository;
import com.effectivemobile.taskmanager.util.NullChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public boolean addUser(NewUserDto newUserDto) {

        log.info("-- Сохранение пользователя:{}", newUserDto);

        if (userRepository.findByNameOrEmail(newUserDto.getName(), newUserDto.getEmail()).isPresent()) {

            log.error("- Такое имя или адрес почты уже есть в базе, пользователь не сохранён");
            return false;
        }

        if (!newUserDto.getPassword().equals(newUserDto.getPasswordConfirm())) {

            log.error("- Повторный пароль введен неверно, пользователь не сохранён");
            return false;
        }

        User userToSave = new User();

        if (newUserDto.getName() != null) {
            userToSave.setName(newUserDto.getName());
        } else {
            userToSave.setName(newUserDto.getEmail());
        }

        userToSave.setEmail(newUserDto.getEmail());
        userToSave.setPassword(passwordEncoder.encode(newUserDto.getPassword()));

        UserFullDto fullDtoToShowInLog = UserMapper.modelToFullDto(userRepository.save(userToSave));

        log.info("-- Пользователь сохранён: {}", fullDtoToShowInLog);

        return true;
    }

    @Override
    @Transactional
    public UserFullDto updateUser(Long userId, NewUserDto updateDto) {

        log.info("-- Обновление пользователя с id={}", userId);

        User userToUpdate = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("- Пользователь с id=%d не найден", userId)));

        if (updateDto.getName() != null || updateDto.getEmail() != null) {

            Optional<User> userToCheck = userRepository.findByNameOrEmail(updateDto.getName(), updateDto.getEmail());

            if (userToCheck.isPresent() && !userToCheck.get().getId().equals(userId)) {

                throw new ConflictOnRequestException(
                        "- Такое имя или адрес почты уже принадлежат другому пользователю, пользователь не обновлён");
            }
        }

        if (!updateDto.getPassword().equals(updateDto.getPasswordConfirm())) {

            throw new ConflictOnRequestException("- Повторный пароль введён неверно, пользователь не обновлён");
        }

        NullChecker.setIfNotNull(userToUpdate::setName, updateDto.getName());
        NullChecker.setIfNotNull(userToUpdate::setEmail, updateDto.getEmail());
        NullChecker.setIfNotNull(userToUpdate::setPassword, updateDto.getPassword());

        UserFullDto fullDtoToReturn = UserMapper.modelToFullDto(userRepository.save(userToUpdate));

        log.info("-- Пользователь обновлён: {}", fullDtoToReturn);

        return fullDtoToReturn;
    }

    @Override
    public UserFullDto getById(Long userId) {

        log.info("-- Возвращение пользователя с id={}", userId);

        UserFullDto fullDtoToReturn = UserMapper.modelToFullDto(userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("- Пользователь с id=%d не найден", userId))));

        log.info("-- Пользователь возвращён: {}", fullDtoToReturn);

        return fullDtoToReturn;
    }

    @Override
    public UserFullDto getByName(String name) {

        log.info("-- Возвращение пользователя с name={}", name);

        UserFullDto fullDtoToReturn = UserMapper.modelToFullDto(userRepository.findByNameOrEmail(name, null)
                .orElseThrow(() -> new NotFoundException(String.format("- Пользователь с name=%s не найден", name))));

        log.info("-- Пользователь возвращён: {}", fullDtoToReturn);

        return fullDtoToReturn;
    }

    @Override
    @Transactional
    public boolean removeById(Long userId) {

        log.info("-- Удаление пользователя с id={}", userId);

        if (!userRepository.existsById(userId)) {

            log.info(String.format("-- Пользователь с id=%d не найден", userId));

            return false;
        }

        userRepository.deleteById(userId);

        log.info(String.format("-- Пользователь с id=%d удалён", userId));

        return true;
    }
}
