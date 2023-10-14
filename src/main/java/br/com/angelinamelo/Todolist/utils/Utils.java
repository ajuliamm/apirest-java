package br.com.angelinamelo.Todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source); // o BeanWrapper é uma interface que fornece uma forma para acessar as propriedades de um objeto dentro do java 
        // O BeanWrapperImpl é a implementação dessa interface;

        PropertyDescriptor[] pds =  src.getPropertyDescriptors();//Obter as propriedades do meu objeto
        
        Set<String> emptyNames = new HashSet<>(); // criar um conjunto com as propriedades que tenho de valores nulos
        
        for(PropertyDescriptor pd: pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null){
                emptyNames.add(pd.getName());
            }
        }

        String[] result =  new String[emptyNames.size()];
        return emptyNames.toArray(result);

    }
}
