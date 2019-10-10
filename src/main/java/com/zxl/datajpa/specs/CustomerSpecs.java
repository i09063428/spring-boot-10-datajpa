package com.zxl.datajpa.specs;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.toArray;

/**
 * @author zxl16
 * @Date 2019/10/10.
 */
public class CustomerSpecs {

    public static <T>Specification<T> byAuto(final EntityManager entityManager,final  T exemple){
        final Class<T> type = (Class<T>) exemple.getClass();
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                EntityType<T> entity = entityManager.getMetamodel().entity(type);
                for (Attribute<T,?> attr : entity.getDeclaredAttributes()){
                   Object attrValue = getValue(exemple,attr);
                   if(attrValue != null){
                       if (attr.getJavaType() == String.class){
                           if(!StringUtils.isEmpty(attrValue)){
                               predicateList.add(criteriaBuilder.like(root.get(attribute(entity,attr.getName(),String.class)), pattern((String)attrValue)));

                           }
                       }else{
                           predicateList.add(criteriaBuilder.equal(root.get(attribute(entity,attr.getName(),attrValue.getClass())),attrValue));
                       }
                   }
                }
                return predicateList.isEmpty()?criteriaBuilder.conjunction():criteriaBuilder.and(toArray(predicateList,Predicate.class));
            }
        };
    }

    static private  String pattern(String attrValue) {
        return "%" + attrValue + "%";
    }

    private static <T> Object getValue(T exemple, Attribute<T, ?> attr) {
        return ReflectionUtils.getField((Field)attr.getJavaMember(),exemple);
    }

    static private<E,T> SingularAttribute<T, E> attribute(EntityType<T> entity, String fileName, Class<E> filedClass) {
        return entity.getDeclaredSingularAttribute(fileName,filedClass);
    }
}
