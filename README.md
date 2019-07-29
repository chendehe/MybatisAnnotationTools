## MybatisAnnotationTools
基于注解的 Mybatis 代码生成工具。
### 功能：
1. 可以自动生成 PO 和 DAO CRUD 的 Java 类
2. 可配置`application.properties`
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
java.model.package=com.xxx.po
# PO 类文件生成路径，"/"结尾
java.model.src.folder=E:/CODE/github/po/
# PO 类文件前缀
java.model.prefix=
# PO 类文件后缀
java.model.suffix=PO
# 是否要生成 DAO
java.dao.enable=true
# DAO 包路径
java.dao.package=com.xxx.dao
# DAO 类文件生成路径，"/"结尾
java.dao.src.folder=E:/CODE/github/dao/
# DAO 类文件前缀
java.dao.prefix=
# DAO 类文件后缀
java.dao.suffix=Dao
```
### 运行
1. 在`pom.xml`文件目录下执行`mvn clean package -Dmaven.test.skip=true`
2. 在生成的`target`目录下取出可以直接执行的 jar 包：`annotation-Tools-1.0-jar-with-dependencies.jar`
3. 在上面这个 jar 包目录下执行`java -jar annotation-Tools-1.0-jar-with-dependencies.jar`即可执行默认配置；也可以把`application.properties`配置文件取出来修改，执行命令的时候在后面加上配置文件绝对路径`{绝对路径}\application.properties`来执行配置文件的位置。

## 例子
有表`t_student`和`t_user`用默认配置会在`E:/CODE/github`生成`dao`和`po`目录，里面的内容如下：
```
├─dao
│      BaseDao.java
│      StudentDao.java
│      UserDao.java
│
└─po
        Page.java
        StudentPO.java
        UserPO.java
```