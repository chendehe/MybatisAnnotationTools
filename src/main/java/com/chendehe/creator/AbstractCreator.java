package com.chendehe.creator;

import org.apache.commons.lang3.StringUtils;

import com.chendehe.entity.Table;
import com.google.common.base.CaseFormat;

import lombok.AllArgsConstructor;

/**
 * @author CDH
 * @since 2019/7/29 19:12
 */
@AllArgsConstructor
abstract class AbstractCreator {

    static final String MODEL_TEMP = "ModelTemplate";
    static final String DAO_TEMP = "DaoTemplate";
    static final String SPLIT_CHAR = "#";
    static final String JAVA = ".java";

    Table table;

    String getDaoName(String tableName) {
        return table.getDaoPrefix()
            .concat(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
                tableName.split(SPLIT_CHAR)[0].replaceAll(table.getTablePrefix(), StringUtils.EMPTY)))
            .concat(table.getDaoSuffix()).trim();
    }

    String getModelName(String tableName) {
        return table.getModelPrefix()
            .concat(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
                tableName.split(SPLIT_CHAR)[0].replaceAll(table.getTablePrefix(), StringUtils.EMPTY)))
            .concat(table.getModelSuffix()).trim();
    }
}
