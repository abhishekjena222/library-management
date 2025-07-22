package com.example.libraryManagement.service;

import com.example.libraryManagement.entity.User;
import com.example.libraryManagement.model.JsonResponse;
import com.example.libraryManagement.model.UserRequest;
import com.example.libraryManagement.repository.UserRepository;
import com.example.libraryManagement.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.libraryManagement.Constants.OBJECT_MAPPER;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ResponseEntity<JsonResponse> addUser(UserRequest userRequest) {
        String validateError = validationUser(userRequest);
        if (validateError != null)
            return ResponseUtil.badRequest(validateError);

        User user = new User(userRequest);
        userRepository.save(user);
        log.info("User Created : name:{} email:{}", user.getName(), user.getEmail());

        return ResponseUtil.success("data_msg", "User created" + OBJECT_MAPPER.valueToTree(user));

    }

    public String validationUser(UserRequest userRequest) {
        if (userRequest.getName() == null || userRequest.getName().isBlank()) return "User Name invalid";
        if (userRequest.getEmail() == null || userRequest.getEmail().isBlank()) return "User Email invalid";
        return null;
    }

    public ResponseEntity<JsonResponse> getUserDetails(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        return optionalUser.map(user -> ResponseUtil.success(OBJECT_MAPPER.valueToTree(user))).orElseGet(() -> ResponseUtil.badRequest("User not available..."));

    }
}
