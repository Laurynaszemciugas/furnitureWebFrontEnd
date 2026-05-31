package com.example.demo.Common.Logic;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObjectConverter {

    @SneakyThrows
    public <T,S> List<T> convert(List<S> from, Class<T> tClass){

        List<T> convertedList = new ArrayList<>();

        for(var fromItem : from){
            T to = tClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(fromItem,to);
            convertedList.add(to);
        }

        return convertedList;

    }

}
