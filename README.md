## MybatisAnnotationTools
基于注解的 Mybatis 代码生成工具，Mybatis-3.5 可用。
### 功能：
1. 自动生成 PO 和 DAO 的 Java 类，DAO 支持分页查询、根据 id 查询、单个插入、批量插入、更新、单个删除、批量删除。  
![Java 文件](https://upload-images.jianshu.io/upload_images/18729964-5b8222f762dad3da.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

UserDao.java 内容如下：
```java
@Mapper
public interface UserDao extends BaseDao<UserDao> {
    /** 分页查询 */
    @Select("select * from t_user limit #{page.currentPage}, #{page.pageSize}")
    List<UserPO> listByPage(@Param("page") Page page);
    /** 根据id查询 */
    @Select("select * from t_user where id = #{id}")
    Optional<UserPO> getById(Serializable id);
    /** 单个插入 */
    @Insert("insert into t_user(id, name, gender, birthday, address, create_time, update_time)  values(#{id}, #{name}, #{gender}, #{birthday}, #{address}, #{createTime}, #{updateTime})")
    void save(UserPO po);
    /** 批量插入 */
    @Insert("<script>insert into t_user(id, name, gender, birthday, address, create_time, update_time) values "
        + "<foreach collection='list' index='index' item='n' separator=','> "
        + "(#{n.id}, #{n.name}, #{n.gender}, #{n.birthday}, #{n.address}, #{n.createTime}, #{n.updateTime})"
        + "</foreach></script>")
    void saveBatch(@Param("list") List<UserPO> list);
    /** 更新 */
    @Update("update t_user set id = #{id}, name = #{name}, gender = #{gender}, birthday = #{birthday}, address = #{address}, create_time = #{createTime}, update_time = #{updateTime} where id = #{id}")
    void update(UserPO po);
    /** 单个删除 */
    @Delete("delete from t_user where id = #{id}")
    void remove(Serializable id);
    /** 批量删除 */
    @Delete("<script>delete from t_user where id in "
        + "<foreach collection='ids' index='index' item='id' open='(' separator=',' close=')'>"
        + "#{id}"
        + "</foreach></script>")
    void removeByIds(@Param("ids") Set<Serializable> ids);
    /** 统计 */
    @Select("select count(*) from t_user")
    int count();
}
```
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
> 可以跳过下载和打包源码直接[下载](https://github.com/chendehe/MybatisAnnotationTools/releases)

1. 在`pom.xml`文件目录下执行`mvn clean package -Dmaven.test.skip=true`
2. 在生成的`target`目录下取出可以直接执行的 jar 包：`annotation-Tools-1.0-jar-with-dependencies.jar`
3. 在上面这个 jar 包目录下执行`java -jar annotation-Tools-1.0-jar-with-dependencies.jar`即可执行默认配置；也可以把`application.properties`配置文件取出来修改，执行命令的时候在后面加上配置文件路径`{文件路径}\application.properties`来执行配置文件的位置。
### 代码结构
 - main 启动类：`Bootstrap.java`
 - 配置文件：`resources/application.properties`目录
 - 模板文件位置：`resources`目录
### 例子
有表`t_student`和`t_user`，用默认配置会在`E:/CODE/github`生成`dao`和`po`目录，里面的内容如下：
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
