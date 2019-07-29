package com.chendehe.creator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.chendehe.entity.Field;
import com.chendehe.entity.Table;
import com.chendehe.utils.FileUtils;

/**
 * @author CDH
 * @since 2019/7/29 11:01
 */
public class ModelCreator extends AbstractCreator {

    public ModelCreator(Table table) {
        super(table);
    }

    public void createModel() {
        fieldsForEach((longTableName, fields) -> FileUtils.writeToFile(
            getModelFilePath(longTableName), createModelFileContent(longTableName))
        );

        createCommonClass();
    }

    private void createCommonClass() {
        String template = FileUtils.getTemplate("common/Page.java");
        template = template.replaceAll("#<modelPackage>", table.getModelPackage());
        template = template.replaceAll("#<createUser>", System.getProperty("user.name"));
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        template = template.replaceAll("#<createTime>", createTime);
        FileUtils.writeToFile(table.getModelFolder().concat("Page.java"), template);
    }

    private String createModelFileContent(String longTableName) {
        final String[] tableNames = longTableName.split(SPLIT_CHAR);
        final String tableName = tableNames[0];
        String fileContent = FileUtils.getTemplate(MODEL_TEMP);
        final String tableComment = tableNames.length > 1 ? tableNames[1] : "";
        fileContent = fileContent.replaceAll("#<tableComment>", tableComment);
        fileContent = fileContent.replaceAll("#<tableName>", longTableName);

        fileContent = fileContent.replaceAll("#<modelPackage>", table.getModelPackage());
        fileContent = fileContent.replaceAll("#<modelName>", getModelName(tableName));
        fileContent = fileContent.replaceAll("#<serialVersionUID>", "1");
        fileContent = fileContent.replaceAll("#<fields>", getFields(longTableName));
        fileContent = fileContent.replaceAll("#<getterSetter>", getGetterSetter(longTableName));

        fileContent = fileContent.replaceAll("#<createUser>", System.getProperty("user.name"));
        final String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        fileContent = fileContent.replaceAll("#<createTime>", createTime);
        return fileContent;
    }

    private String getFields(String tableName) {
        List<Field> fields = table.getTableFields().get(tableName);
        StringBuilder builder = new StringBuilder();
        String template = "    /** #<modelRemarks> */\n    private #<javaType> #<javaFieldName>;\n";
        for (Field field : fields) {
            String property = template.replaceAll("#<javaType>", field.getJavaType());
            property = property.replaceAll("#<javaFieldName>", field.getJavaFieldName());
            String remarks = field.getRemarks();
            if (StringUtils.isNotBlank(remarks)) {
                property = property.replaceAll("#<modelRemarks>", remarks);
            } else {
                property = property.replaceAll("#<modelRemarks>", "");
            }
            builder.append(property);
        }
        return builder.toString();
    }

    private String getGetterSetter(String tableName) {
        List<Field> fields = table.getTableFields().get(tableName);
        return fields.stream()
            .map(field -> new StringBuilder().append(getGetterContent(field)).append(getSetterContent(field)))
            .collect(Collectors.joining());
    }

    private String getGetterContent(Field field) {
        String fieldType = field.getFieldType();
        String templateGetter =
            "    public #<javaType> get#<metholdPostName>() {\n        "
                + "return #<javaFieldName>;\n    "
                + "}\n\n";
        if ("BIT".equals(fieldType)) {
            templateGetter =
                "    public #<javaType> is#<metholdPostName>() {\n        "
                    + "return #<javaFieldName>;\n    "
                    + "}\n\n";
        }
        return getMetholdContent(templateGetter, field);
    }

    private String getSetterContent(Field field) {
        String templateSetter;
        if ("String".equalsIgnoreCase(field.getJavaType())) {
            templateSetter = "    public void set#<metholdPostName>(#<javaType> #<javaFieldName>) {\n"
                + "        this.#<javaFieldName> = #<javaFieldName> == null ? null : #<javaFieldName>.trim();\n    "
                + "}\n\n";
        } else {
            templateSetter = "    public void set#<metholdPostName>(#<javaType> #<javaFieldName>) {\n        "
                + "this.#<javaFieldName> = #<javaFieldName>;\n    " + "}\n\n";

        }
        return getMetholdContent(templateSetter, field);
    }

    private String getMetholdContent(String template, Field field) {
        StringBuilder builder = new StringBuilder();
        String methold = template.replaceAll("#<javaType>", field.getJavaType());
        String javaFieldName = field.getJavaFieldName();
        methold = methold.replaceAll("#<metholdPostName>",
            javaFieldName.substring(0, 1).toUpperCase() + javaFieldName.substring(1));
        methold = methold.replaceAll("#<javaFieldName>", field.getJavaFieldName());
        builder.append(methold);
        return builder.toString();
    }

}
