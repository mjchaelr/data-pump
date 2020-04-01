package com.mjchael.datapump.core.data;

import com.mjchael.datapump.core.model.AllTable;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface AllTableRepository extends Repository <AllTable, String> {
    Optional<AllTable>  findByTableNameAndOwner(String tableName, String Owner);
}