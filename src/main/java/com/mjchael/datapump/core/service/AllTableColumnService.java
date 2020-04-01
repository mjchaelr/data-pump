package com.mjchael.datapump.core.service;

import com.mjchael.datapump.core.model.AllTableColumn;
import com.mjchael.datapump.core.data.AllTableColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Service
public class AllTableColumnService {

    private AllTableColumnRepository allTableColumnRepository;

    @Autowired
    public AllTableColumnService(AllTableColumnRepository allTableColumnRepository) {
        this.allTableColumnRepository = allTableColumnRepository;
    }

    public List<AllTableColumn> findByTableNameAndOwner(String tableName, String owner){
        Assert.notNull(tableName, "The given tableName must not be null!");
        Assert.notNull(owner, "The given owner must not be null!");

        return allTableColumnRepository.findByTableNameAndOwner(tableName, owner);
    }
}
