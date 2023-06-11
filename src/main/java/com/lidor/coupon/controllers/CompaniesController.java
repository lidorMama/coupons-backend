package com.lidor.coupon.controllers;

import com.lidor.coupon.dto.CompanyData;
import com.lidor.coupon.entities.Company;
import com.lidor.coupon.exceptions.ServerException;
import com.lidor.coupon.logic.CompaniesLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/company")
public class CompaniesController {
    private CompaniesLogic companiesLogic;

    @Autowired
    public CompaniesController(CompaniesLogic companiesLogic) {
        this.companiesLogic = companiesLogic;
    }

    @PostMapping
    public long createCompany(@RequestBody Company company) throws ServerException {
        return companiesLogic.createCompany(company);
    }

    @PutMapping
    public void updateCompany(@RequestBody Company company) throws ServerException {
        companiesLogic.updateCompany(company);
    }

    @GetMapping("{companyId}")
    public CompanyData getCompany(@PathVariable("companyId") long id) throws ServerException {
        return companiesLogic.getCompany(id);
    }

    @DeleteMapping("{companyId}")
    public void deleteCompany(@PathVariable("companyId") long id) throws ServerException {
        companiesLogic.removeCompany(id);
    }

    @GetMapping
    public List<CompanyData> getAllCompanies(@RequestParam("page") int pageNumber) throws ServerException {
        return companiesLogic.getAllCompanies(pageNumber);
    }

}
