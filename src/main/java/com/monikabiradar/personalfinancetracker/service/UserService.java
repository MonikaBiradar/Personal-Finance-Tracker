package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.UserStatus;
import com.monikabiradar.personalfinancetracker.exception.UserConflictException;
import com.monikabiradar.personalfinancetracker.exception.UserNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ExpenseService expenseService;

    public UserService(UserRepository userRepository,
                       ExpenseService expenseService) {
        this.userRepository = userRepository;
        this.expenseService = expenseService;
    }

    public User addUser(String userName, String phoneNumber, String email) {

        if(userRepository.existsByEmail(email)) {
            throw new UserConflictException("Email already registered.");
        }

        User user = new User();

        user.setUserName(userName);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setUserStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        expenseService.addExpense("Transport", user);
        expenseService.addExpense("Food", user);
        expenseService.addExpense("Light Bill", user);
        expenseService.addExpense("Clothes", user);

        return user;
    }

    public User deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException(
                        "User "+ userId + " Not Found."));

        user.setUserStatus(UserStatus.INACTIVE);
        userRepository.save(user);

        return user;
    }
}
