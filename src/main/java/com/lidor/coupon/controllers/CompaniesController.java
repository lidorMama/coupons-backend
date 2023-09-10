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
    public void createCompany(@RequestHeader String authorization, @RequestBody Company company) throws ServerException {
        companiesLogic.createCompany(authorization, company);
    }

    @PutMapping
    public void updateCompany(@RequestHeader String authorization, @RequestBody Company company) throws ServerException {
        companiesLogic.updateCompany(authorization, company);
    }

    @GetMapping("{companyId}")
    public Company getCompany(@PathVariable("companyId") long id) throws ServerException {
        return companiesLogic.getCompany(id);
    }

    @DeleteMapping("{companyId}")
    public void deleteCompany(@RequestHeader String authorization, @PathVariable("companyId") long id) throws ServerException {
        companiesLogic.removeCompany(authorization, id);
    }

    @GetMapping()
    public List<CompanyData> getAllCompanies() throws ServerException {
        return companiesLogic.getAllCompanies();
    }

}
