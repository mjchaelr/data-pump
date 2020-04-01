package com.mjchael.datapump.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExportTableSpecification {
    private String owner;
    private String tableName;
    private String preInsertScript;
    private String postInsertScript;
    private List<String> primaryKeyField;
    private String whereClause;
    private String wiki;
    private List<ExportTableSpecification> dependencies;
    private List<ExportTableSpecification> relatedTables;
}
