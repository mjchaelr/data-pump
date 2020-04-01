package com.mjchael.datapump.core.data;

import java.util.List;

public interface CustomRepository {

    List<Object[]> executeQuery(String sqlStatement);
}
