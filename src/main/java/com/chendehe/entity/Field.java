package com.chendehe.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author CDH
 * @since 2019/7/29 11:01
 */
@Data
@NoArgsConstructor
public class Field {

    private String fieldName;

    private String fieldType;

    private String javaFieldName;

    private String remarks;

    private boolean key;

    private int precision;

    private int scale;

    private boolean nullable;

    public String getRemarks() {
        return nullable ? remarks + " (Not Null)" : remarks;
    }

    public String getJavaType() {
        if (fieldType.toUpperCase().startsWith("VARCHAR2")) {
            return "String";
        }
        if (fieldType.toUpperCase().startsWith("CHAR")) {
            return "String";
        }
        if (fieldType.toUpperCase().startsWith("CLOB")) {
            return "String";
        }
        if (fieldType.toUpperCase().startsWith("DATE")) {
            return "LocalDate";
        }
        if (fieldType.toUpperCase().startsWith("NUMBER")) {
            if (scale > 0) {
                return "BigDecimal";
            }
            return "Integer";
        }
        if (fieldType.toUpperCase().startsWith("TIMESTAMP")) {
            return "LocalDateTime";
        }
        if (fieldType.toUpperCase().startsWith("LONG")) {
            return "String";
        }
        if (fieldType.toUpperCase().startsWith("BIGINT UNSIGNED")) {
            return "Long";
        }
        if (fieldType.toUpperCase().startsWith("BIGINT")) {
            return "Long";
        }
        if (fieldType.toUpperCase().startsWith("VARCHAR")) {
            return "String";
        }
        if (fieldType.toUpperCase().startsWith("TINYINT")) {
            return "Integer";
        }
        if (fieldType.toUpperCase().startsWith("INT")) {
            return "Integer";
        }
        if (fieldType.toUpperCase().startsWith("FLOAT")) {
            return "Float";
        }
        if (fieldType.toUpperCase().startsWith("DOUBLE")) {
            return "Double";
        }

        throw new RuntimeException("javaType not found! fieldType is " + fieldType);

    }

}
