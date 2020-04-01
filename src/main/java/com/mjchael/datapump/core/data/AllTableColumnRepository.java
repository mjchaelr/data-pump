package com.mjchael.datapump.core.data;

import com.mjchael.datapump.core.model.AllTableColumn;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AllTableColumnRepository extends Repository <AllTableColumn, String> {
    List<AllTableColumn> findByTableNameAndOwner(String tableName, String Owner);
}