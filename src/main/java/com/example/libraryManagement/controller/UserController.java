package com.example.libraryManagement.controller;

import com.example.libraryManagement.model.JsonResponse;
import com.example.libraryManagement.model.UserRequest;
import com.example.libraryManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<JsonResponse> addUser(@RequestBody UserRequest userRequest){
        return userService.addUser(userRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResponse> getUser(@PathVariable long id){
        return userService.getUserDetails(id);
    }




}
