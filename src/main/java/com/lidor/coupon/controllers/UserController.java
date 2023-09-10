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
        String token = usersLogic.login(userLoginData);
        return token;
    }

    @PutMapping
    public void updateUser(@RequestHeader String authorization, @RequestBody User userEntity) throws ServerException {
        usersLogic.updateUser(authorization, userEntity);
    }

    @GetMapping("{userId}")
    public UserData getUser(@RequestHeader String authorization) throws ServerException {
        return usersLogic.getUser(authorization);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@RequestHeader String authorization, @PathVariable("userId") long id) throws ServerException {
        usersLogic.removeUser(authorization, id);
    }

    @GetMapping({"/byPage"})
    public List<UserData> getAllUsers(@RequestHeader String authorization, @RequestParam("page") int pageNumber) throws ServerException {
        return usersLogic.getAllUsers(authorization, pageNumber);
    }

    @GetMapping({"/byCompanyId"})
    public Iterable<UserData> getAllUsersByCompanyId(@RequestParam("companyId") long companyId, @RequestParam("page") int pageNumber) throws ServerException {
        return usersLogic.getAllCompanyUsers(companyId, pageNumber);
    }

    @GetMapping({"/byUserType"})
    public Iterable<UserData> findAllByUserType(@RequestHeader String authorization, @RequestParam("page") int pageNumber, @RequestParam("userType") UserType userType) throws ServerException {
        return usersLogic.findAllByUserType(authorization, pageNumber, userType);
    }

    @PutMapping("/{userId}/updateUsername")
    public void updateUsername(@RequestHeader String authorization,@PathVariable("userId") long id, @RequestParam String newUsername) throws ServerException {
        usersLogic.updateUserName(authorization,id, newUsername);
    }
}
