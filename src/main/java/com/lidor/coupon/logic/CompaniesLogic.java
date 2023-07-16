package com.lidor.coupon.logic;

import com.lidor.coupon.Consts.Constants;
import com.lidor.coupon.dal.ICompanyDal;
import com.lidor.coupon.dto.CompanyData;
import com.lidor.coupon.entities.Company;
import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompaniesLogic {
    private ICompanyDal companiesDal;

    @Autowired
    public CompaniesLogic(ICompanyDal companiesDal) {
        this.companiesDal = companiesDal;
    }

    public void createCompany(Company company) throws ServerException {
        companyValidation(company);
        companyExistByName(company.getName());
        try {
            companiesDal.save(company);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to create company " + company.getName());
        }

    }

    public void updateCompany(Company company) throws ServerException {
        validCompanyExist(company.getId());
        companyValidation(company);
        try {
            companiesDal.save(company);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to update company " + company.getName());
        }
    }

    public CompanyData getCompany(long companyId) throws ServerException {
        validCompanyExist(companyId);
        try {
            CompanyData company = companiesDal.getCompany(companyId);
            return company;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to create company " + companyId);
        }

    }

    public void removeCompany(long companyId) throws ServerException {
        validCompanyExist(companyId);
        try {
            companiesDal.deleteById(companyId);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to remove company " + companyId);
        }
    }

    public List<CompanyData> getAllCompanies() throws ServerException {
        try {
            List<CompanyData> companies = companiesDal.getAllCompanies();
            return companies;
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, "Failed to get all companies");
        }

    }

    void validCompanyExist(long companyId) throws ServerException {
        if (ValidatorUtils.isIdValid(companyId) == false) {
            throw new ServerException(ErrorType.COMPANY_DOES_NOT_EXIST, "Company dose not exist " + companyId);
        }
        if (!companiesDal.existsById(companyId)) {
            throw new ServerException(ErrorType.COMPANY_DOES_NOT_EXIST, "company dose not exist " + companyId);
        }
    }

    private void companyValidation(Company company) throws ServerException {
        if (!ValidatorUtils.isNameLengthValid(company.getName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Name length wrong" + company.getName());
        }
    }

    private void companyExistByName(String name) throws ServerException {
        boolean isNmeExist = companiesDal.existsByName(name);
        if (isNmeExist) {
            throw new ServerException(ErrorType.NAME_ALREADY_EXIST, "company name already exist " + name);
        }
    }


}
