package com.lidor.coupon.dal;

import com.lidor.coupon.dto.CustomerData;
import com.lidor.coupon.entities.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerDal extends CrudRepository<Customer, Long> {
    @Query("SELECT NEW com.lidor.coupon.dto.CustomerData(cs.user.id, cs.user.userName, cs.firstName, cs.lastName, cs.address, cs.amountOfKids, cs.phoneNumber) FROM Customer cs Join User us ON cs.id = us.id")
    List<CustomerData> findAll(Pageable pageable);

    @Query("SELECT NEW com.lidor.coupon.dto.CustomerData(cs.user.id, cs.user.userName, cs.firstName, cs.lastName, cs.address, cs.amountOfKids, cs.phoneNumber) FROM Customer cs Join User us ON cs.id = us.id WHERE cs.id= :customerId")
    CustomerData findCustomer(@Param("customerId") long customerId);

}
