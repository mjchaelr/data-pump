package com.mjchael.datapump.core.model.compositekey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllTableColumnId implements Serializable {
    /**
     * This class was implemented to test cases of composite key.
     *
     */
    private String tableName;
    private String owner;
    private String columnName;
}
