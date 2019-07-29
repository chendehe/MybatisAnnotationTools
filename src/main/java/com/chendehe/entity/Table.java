package com.chendehe.entity;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author CDH
 * @since 2019/7/29 11:01
 */
@Data
public class Table {
    private String modelPackage;
    private String modelFolder;
    private String daoPackage;
    private String daoFolder;
    private String modelPrefix;
    private String modelSuffix;
    private String daoPrefix;
    private String daoSuffix;
    private String tablePrefix;
    /**
     * K:tableName#comment V:fields
     */
    private Map<String, List<Field>> tableFields;
}
