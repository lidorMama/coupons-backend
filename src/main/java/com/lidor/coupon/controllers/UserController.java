package com.lidor.coupon.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lidor.coupon.dto.UserData;
import com.lidor.coupon.dto.UserLoginData;
import com.lidor.coupon.entities.User;
import com.lidor.coupon.enums.UserType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.logic.UsersLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UsersLogic usersLogic;

    @Autowired
    public UserController(UsersLogic userEntityLogic) {
        this.usersLogic = userEntityLogic;
    }

    @PostMapping
    public void createUser(@RequestBody User userEntity) throws ServerException {
        usersLogic.createUser(userEntity);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginData userLoginData) throws ServerException, JsonProcessingException {
       String token =usersLogic.login(userLoginData);
        return token;
    }

    @PutMapping
    public void updateUser(@RequestBody User userEntity) throws ServerException {
        usersLogic.updateUser(userEntity);
    }

    @GetMapping("{userId}")
    public UserData getUser(@PathVariable("userId") long userId) throws ServerException {
        return usersLogic.getUser(userId);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") long id) throws ServerException {
        usersLogic.removeUser(id);
    }

    @GetMapping({"/byPage"})
    public List<UserData> getAllUsers(@RequestParam("page") int pageNumber) throws ServerException {
        return usersLogic.getAllUsers(pageNumber);
    }

    @GetMapping({"/byCompanyId"})
    public Iterable<UserData> getAllUsersByCompanyId(@RequestParam("companyId") long companyId, @RequestParam("page") int pageNumber) throws ServerException {
        return usersLogic.getAllCompanyUsers(companyId,pageNumber);
    }
    @GetMapping({"/byUserType"})
    public Iterable<UserData> findAllByUserType(@RequestHeader String authorization, @RequestParam("page") int pageNumber, UserType userType) throws ServerException {
        return usersLogic.findAllByUserType(authorization,pageNumber,userType);
    }

 }
