package com.zxl.datajpa.web;

import com.zxl.datajpa.dao.PersonRepository;
import com.zxl.datajpa.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zxl16
 * @Date 2019/10/10.
 */
@RestController
public class DataController {
    @Autowired
    PersonRepository personRepository;

   @RequestMapping("/save")
   public Person save(String name,String address,Integer age){
       Person p = new Person(name,age,address);
       Person person = personRepository.save(p);
       return person;
   }

   @RequestMapping("/q1")
   public List<Person> q1(String address){
       List<Person> listPs = personRepository.findByAddress(address);
       return listPs;
   }

   @RequestMapping("/q2")
   public Person q2(String name ,String address){
       Person person = personRepository.findByNameAndAddress(name,address);
       return person;
   }

   @RequestMapping("/q3")
   public Person q3(String name ,String address){
       Person person = personRepository.withNameAndAddressQuery(name,address);
       return person;
   }

    @RequestMapping("/q4")
    public Person q4(String name ,String address){
        Person person = personRepository.withNameAndAddressNameQuery(name,address);
        return person;
    }

    @RequestMapping("/sort")
    public List<Person> sort(){
        List<Person> listPs = personRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        return listPs;
    }

    @RequestMapping("/page")
    public Page<Person> page(){
        Page<Person> pagePs = personRepository.findAll(new PageRequest(1, 2));
        return pagePs;
    }

    @RequestMapping("/auto")
    public Page<Person> auto(Person person){
        Page<Person> pagePeople = personRepository.findByAuto(person,new PageRequest(0,10));
        return  pagePeople;
    }
}
