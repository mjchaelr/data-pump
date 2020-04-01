package com.mjchael.datapump.core.service;

import com.mjchael.datapump.core.data.CustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomQueryService {
    @Autowired
    private CustomRepository customRepository;

//    public CustomQueryService(CustomRepository customRepository){
//        this.customRepository = customRepository;
//    }
    // to investigate use of above code.

    public List<Object[]> executeQuery(String sql){
        return customRepository.executeQuery(sql);
    }

}
