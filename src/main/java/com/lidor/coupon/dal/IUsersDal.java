package com.lidor.coupon.dal;

import com.lidor.coupon.dto.SuccessfulLoginData;
import com.lidor.coupon.dto.UserData;
import com.lidor.coupon.entities.User;
import com.lidor.coupon.enums.UserType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsersDal extends CrudRepository<User, Long> {
    @Query("SELECT NEW com.lidor.coupon.dto.UserData(us.id, us.userName, us.userType, us.company.name) FROM User us Join Company cm ON us.company.id = cm.id")
    List<UserData> findAll(Pageable pageable);

    List<User> findAllByUserType(Pageable pageable, UserType userType);


    @Query("SELECT NEW com.lidor.coupon.dto.UserData(us.id, us.userName, us.userType, us.company.name) FROM User us Join Company cm ON us.company.id = cm.id WHERE us.company.id= :companyId")
    List<UserData> findAllByCompany(@Param("companyId") long companyId, Pageable pageable);

    boolean existsByUserName(String userName);

    @Query("SELECT NEW com.lidor.coupon.dto.SuccessfulLoginData(us.id, us.userType, us.company.id) FROM User us WHERE us.userName= :userName AND us.password= :password ")
    SuccessfulLoginData login(@Param("userName") String userName,@Param("password") String password);

    @Query("UPDATE User u SET u.userName = :newUserName WHERE u.id = :userId")
    void updateUserName(@Param("userId") Long userId, @Param("newUserName") String newUserName);}
