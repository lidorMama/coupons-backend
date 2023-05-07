package com.lidor.coupon.dal;

import com.lidor.coupon.dto.CompanyData;
import com.lidor.coupon.entities.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompanyDal extends CrudRepository<Company, Long> {

    @Query("SELECT NEW com.lidor.coupon.dto.CompanyData(c.id, c.name, c.address) FROM Company c ")
    List<CompanyData> findAll(Pageable pageable);

    boolean existsByName(String name);

}
