package com.lidor.coupon.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lidor.coupon.Consts.Constants;
import com.lidor.coupon.dal.IUsersDal;
import com.lidor.coupon.dto.SuccessfulLoginData;
import com.lidor.coupon.dto.UserData;
import com.lidor.coupon.dto.UserLoginData;
import com.lidor.coupon.entities.User;
import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.util.JWTUtils;
import com.lidor.coupon.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsersLogic {
    private IUsersDal usersDal;
    private CompaniesLogic companiesLogic;

    @Autowired
    public UsersLogic(IUsersDal usersDal, CompaniesLogic companiesLogic) {
        this.usersDal = usersDal;
        this.companiesLogic = companiesLogic;
    }

    public void createUser(User user) throws ServerException {
        userValidation(user);
        userExistByName(user.getUserName());
        usersDal.save(user);
    }

    public void updateUser(User user) throws ServerException {
        validUserExistById(user.getId());
        userValidation(user);
        usersDal.save(user);
    }

    public UserData getUser(long userId) throws ServerException {
        validUserExistById(userId);
        UserData user = usersDal.findUser(userId);
        return user;
    }

    public void removeUser(long userId) throws ServerException {
        validUserExistById(userId);
        usersDal.deleteById(userId);
    }

    public List<UserData> getAllUsers(int pageNumber) throws ServerException {
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        List<UserData> users = usersDal.findAll(pageable);
        return users;
    }

    public List<UserData> getAllCompanyUsers(long companyId, int pageNumber) throws ServerException {
        companiesLogic.validCompanyExist(companyId);
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        List<UserData> companyUsers = usersDal.findAllByCompany(companyId, pageable);
        return companyUsers;
    }

    public String login(UserLoginData userLoginData) throws ServerException, JsonProcessingException {
        SuccessfulLoginData user = usersDal.login(userLoginData.getUserName(), userLoginData.getPassword());
        if (user == null) {
            throw new ServerException(ErrorType.LOGIN_FAILURE, "The user name and password are not matched, Username: " + userLoginData.getUserName() + " ,Password: " + userLoginData.getPassword());
        }
        String token = JWTUtils.createJWT(user);
        return token;
    }

    void userExistByName(String userName) throws ServerException {
        boolean isNameExist = usersDal.existsByUserName(userName);
        if (isNameExist) {
            throw new ServerException(ErrorType.NAME_ALREADY_EXIST, "name already exist " + userName);
        }
    }

    void validUserExistById(long userId) throws ServerException {
        if (ValidatorUtils.isIdValid(userId) == false) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST, "user does not exist " + userId);
        }
        if (!usersDal.existsById(userId)) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST, "user does not exist " + userId);
        }
    }

    void userValidation(User user) throws ServerException {
        isEmailValid(user.getUserName());
        if (user.getPassword().isEmpty()) {
            throw new ServerException(ErrorType.INVALID_PASSWORD, "password is empty " + user.getPassword());
        }
        if (user.getPassword().length() < 6) {
            throw new ServerException(ErrorType.INVALID_PASSWORD, "password to short " + user.getPassword());
        }
        if (user.getPassword().length() > 15) {
            throw new ServerException(ErrorType.INVALID_PASSWORD, "password to long " + user.getPassword());
        }
        if (user.getCompany() != null && user.getCompany().getId() != null) {
            companyValid(user);
        }
    }

    private void companyValid(User user) throws ServerException {
        companiesLogic.validCompanyExist(user.getCompany().getId());
    }

    private void isEmailValid(String email) throws ServerException {
        final String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new ServerException(ErrorType.INVALID_NAME, "wrong name " + email);
        }
    }
}



