package com.mjchael.datapump.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ALL_TABLES")
public class AllTable implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="TABLE_NAME")
    private String tableName;

    @Column(name="OWNER")
    private String owner;

    @Column(name="TABLESPACE_NAME")
    private String tablespaceName;
}
