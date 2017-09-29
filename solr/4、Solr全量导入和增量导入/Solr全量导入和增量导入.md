# 一、Solr全量导入

全量导入可以查看 `3. solr配置核心核心`一文，其配置步骤就是全量导入的步骤

# 二、增量导入

### 1、修改数据库
前面已经创建好了一个commodity的表，这里为了能够进行增量导入，需要新增一个字段，类型为TIMESTAMP，默认值为CURRENT_TIMESTAMP。

有了这样一个字段，Solr才能判断增量导入的时候，哪些数据是新的。
因为Solr本身有一个默认值last_index_time，记录最后一次做full import或者是delta import(增量导入）的时间，这个值存储在文件conf目录的dataimport.properties文件中。


### 2、可以重新配置dataImportHandler,或者直接修改前面编写的data-config.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<dataConfig>
    <dataSource type="JdbcDataSource" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/testdb" user="root" password="sr107"/>
　　<document name="hjx">
        <entity name="commodity" pk="id" query="select id,cname,btid,stid,cprice,cremain from commodity"
			deltaImportQuery="select id,cname,btid,stid,cprice,cremain from commodity where id='${dih.delta.id}'"
			deltaQuery="select id from commodity where updateTime>'${dataimporter.last_index_time}'"
            transformer="RegexTransformer"
		>
            <field column="id" name="id" />
            <field column="cname" name="cname" />
            <field column="btid" name="btid" />
            <field column="stid" name="stid" />
            <field column="cprice" name="cprice" />
			<field column="cremain" name="cremain" />
        </entity>
    </document>
</dataConfig>
```
解读：
```
1、首先按照query指定的SQL语句查询出符合条件的记录。
2、然后从这些数据中根据deltaQuery指定的SQL语句查询出所有需要增量导入的数据的ID号。
3、最后根据deltaImportQuery指定的SQL语句返回所有这些ID的数据，即为这次增量导入所要处理的数据。
核心思想是：通过内置变量“${dih.delta.id}”和 “${dataimporter.last_index_time}”来记录本次要索引的id和最近一次索引的时间。
注意：刚新加上的UpdateTime字段也要在field属性中配置，同时也要在schema.xml文件中配置：<field name="updateTime" type="date" indexed="true" stored="true" />
```
