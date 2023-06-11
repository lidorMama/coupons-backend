package com.lidor.coupon.logic;

import com.lidor.coupon.Consts.Constants;
import com.lidor.coupon.dal.ICompanyDal;
import com.lidor.coupon.dto.CompanyData;
import com.lidor.coupon.entities.Company;
import com.lidor.coupon.enums.ErrorType;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompaniesLogic {
    private ICompanyDal companiesDal;

@Autowired
    public CompaniesLogic(ICompanyDal companiesDal) {
        this.companiesDal = companiesDal;
    }

    public long createCompany(Company company) throws ServerException {
        companyValidation(company);
        companyExistByName(company.getName());
        companiesDal.save(company);
        long id = company.getId();
        return id;

    }

    public void updateCompany(Company company) throws ServerException {
        validCompanyExist(company.getId());
        companyValidation(company);
        companiesDal.save(company);
    }

    public CompanyData getCompany(long companyId) throws ServerException {
        validCompanyExist(companyId);
        CompanyData company = companiesDal.getCompany(companyId);
        return company;
    }

    public void removeCompany(long companyId) throws ServerException {
        validCompanyExist(companyId);
        companiesDal.deleteById(companyId);
    }

    public List<CompanyData> getAllCompanies(int pageNumber) throws ServerException {
        Pageable pageable = PageRequest.of(pageNumber -1, Constants.AMOUNT_OF_ITEMS_IN_PAGE);
        List<CompanyData> companies = companiesDal.findAll(pageable);
        return companies;
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
