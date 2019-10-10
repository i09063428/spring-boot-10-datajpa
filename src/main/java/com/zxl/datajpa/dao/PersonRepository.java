package com.zxl.datajpa.dao;

import com.zxl.datajpa.domain.Person;
import com.zxl.datajpa.support.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zxl16
 * @Date 2019/10/10.
 */
public interface PersonRepository extends CustomRepository<Person, Long> {

    List<Person> findByAddress (String address);

    Person findByNameAndAddress (String name, String address);

    @Query("select p from Person p where p.name= :name and p.address= :address")
    Person withNameAndAddressQuery (@Param("name") String name, @Param("address") String address);

    Person withNameAndAddressNameQuery(String name,String address);

}
