package com.mjchael.datapump.core.model;

import com.mjchael.datapump.core.model.compositekey.AllTableColumnId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = AllTableColumn.TABLE_NAME)
@IdClass(AllTableColumnId.class)
public class AllTableColumn implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME= "ALL_TAB_COLUMNS";

    @Id
    @Column(name="TABLE_NAME")
    private String tableName;

    @Id
    @Column(name="OWNER")
    private String owner;

    @Id
    @Column(name="COLUMN_NAME")
    private String columnName;

    @Column(name="DATA_TYPE")
    private String dataType;

    @Column(name="DATA_LENGTH")
    private String dataLength;

    @Column(name="DATA_PRECISION")
    private String dataPrecision;

    @Column(name="NULLABLE")
    private String nullable;

    @Column(name="COLUMN_ID")
    private Integer columnId;

}
