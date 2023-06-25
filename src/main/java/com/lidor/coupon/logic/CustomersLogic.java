package com.lidor.coupon.logic;

import com.lidor.coupon.Consts.Constants;
import com.lidor.coupon.dal.ICustomerDal;
import com.lidor.coupon.dto.CustomerData;
import com.lidor.coupon.entities.Customer;
import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.util.HashUtils;
import com.lidor.coupon.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersLogic {
    private UsersLogic usersLogic;
    private ICustomerDal customersDal;

    @Autowired
    public CustomersLogic(UsersLogic usersLogic, ICustomerDal customersDal) {
        this.usersLogic = usersLogic;
        this.customersDal = customersDal;
    }

    public void addCustomer(Customer customer) throws ServerException {
        validateCustomer(customer);
        usersLogic.userValidation(customer.getUser());
        usersLogic.userExistByName(customer.getUser().getUserName());
        String hexString = HashUtils.computeSHA256Hash(customer.getUser().getPassword());
        customer.getUser().setPassword(hexString);
        try {
            customersDal.save(customer);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to add customer" + customer.getUser().getUserName());
        }
    }

    public Customer getCustomer(long customerId) throws ServerException {
        customerExistById(customerId);
        try {
            Customer customer = customersDal.findById(customerId);
            return customer;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get customer" + customerId);
        }
    }

    public void removeCustomer(long customerId) throws ServerException {
        customerExistById(customerId);
        try {
            customersDal.deleteById(customerId);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to remove customer" + customerId);
        }
    }

    public void updateCustomer(Customer customer) throws ServerException {
        validateCustomer(customer);
        try {
            customersDal.save(customer);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to update customer" + customer.getUser().getUserName());
        }
    }

    public List<CustomerData> getCustomers(int pageNumber) throws ServerException {
        Pageable pageable = PageRequest.of(pageNumber - 1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        try {
            List<CustomerData> customers= customersDal.findAll(pageable);
            return customers;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get customers");
        }
    }

    private void customerExistById(Long customerId) throws ServerException {
        if (!ValidatorUtils.isIdValid(customerId)) {
            throw new ServerException(ErrorType.CUSTOMER_DOES_NOT_EXIST, " " + customerId);
        }
    }

    private void validateCustomer(Customer customer) throws ServerException {
        if (customer.getAddress() != null) {
            if (customer.getAddress().length() < 1) {
                throw new ServerException(ErrorType.INVALID_ADDRESS, customer.getAddress());
            }
            if (customer.getAddress().length() > 45) {
                throw new ServerException(ErrorType.INVALID_ADDRESS, customer.getAddress());
            }
        }
//        if (customer.getPhoneNumber() != null) {
//            if (!ValidatorUtils.isNumberValid(customer.getPhoneNumber())) {
//                throw new ServerException(ErrorType.INVALID_PHONE_NUMBER, customer.getPhoneNumber());
//            }
//        }
    }
}
