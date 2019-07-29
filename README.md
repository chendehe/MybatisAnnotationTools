# MybatisAnnotationTools
适用基于注解的 Mybatis 的开发。
##功能：  
1. 可以自动生成 PO 和 DAO CRUD 的 Java 类
2. 可配置
```properties
# MySQL 连接配置
mysql.datasource.driver-class-name=com.mysql.jdbc.Driver
mysql.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false
mysql.datasource.username=root
mysql.datasource.password=
# 表前缀，生成类时会去掉这个前缀
mysql.datasource.table.prefix=t_
# 是否要生成 PO
java.model.enable=true
# PO 包路径
java.model.package=com.chendehe.po
# PO 类文件生成路径
java.model.src.folder=E:/CODE/po/
# PO 类文件前缀
java.model.prefix=
# PO 类文件后缀
java.model.suffix=PO
# 是否要生成 DAO
java.dao.enable=true
# DAO 包路径
java.dao.package=com.chendehe.dao
# DAO 类文件生成路径
java.dao.src.folder=E:/CODE/dao/
# DAO 类文件前缀
java.dao.prefix=
# DAO 类文件后缀
java.dao.suffix=Dao
```

