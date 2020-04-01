package com.mjchael.datapump.core.service;

import com.mjchael.datapump.core.data.AllTableRepository;
import com.mjchael.datapump.core.model.AllTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class AllTableService{

    private AllTableRepository allTableRepository;

    @Autowired
    public AllTableService(AllTableRepository allTableRepository) {
        this.allTableRepository = allTableRepository;
    }

    public AllTable findByTableNameAndOwner(String tableName, String owner){
        Assert.notNull(tableName, "The given tableName must not be null!");
        Assert.notNull(owner, "The given owner must not be null!");


        Optional<AllTable> allTable = allTableRepository.findByTableNameAndOwner(tableName, owner);

        return allTable.orElseThrow(()-> { return new AssertionError("table"+ tableName +" not found in schema "+owner);});
    }
}
