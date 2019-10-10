
# A simple JDBC wrapper

[![Build Status](https://travis-ci.com/Huskehhh/MySQL.svg?branch=master)](https://travis-ci.com/Huskehhh/MySQL)

A simple, clean and effective JDBC wrapper built on top of [HikariCP](https://github.com/brettwooldridge/HikariCP)

### Setting up your project workspace!

#### Maven
To integrate this library in your project using maven, add these to your pom.xml
```xml
<repository>
    <id>husk</id>
    <url>http://maven.husk.pro/repository/internal/</url>
</repository>
```

```xml
<dependency>
    <groupId>pro.husk</groupId>
    <artifactId>mysql</artifactId>
    <version>1.1</version>
</dependency>
```

##### Note: it is assumed that mysql-connector-java is provided

If it is not, please also add

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>VERSION</version>
</dependency>
```

Versions can be found [here](https://mvnrepository.com/artifact/mysql/mysql-connector-java)

##### Don't use maven?
Alternatively, you can also just supply these classes in your local project workspace!

### Usage
#### Create the database
```Java
// Create database
MySQL mysql = new MySQL(host, port, database, username, password, useSSL);
```
#### Query
```Java
// Execute query
ResultSet results = mysql.query("SELECT * from table WHERE id = 1;");

if(results != null) {
  // do something
}
```
#### Update
```java
int resultCode = mysql.update("INSERT INTO `whitelist` (`uuid`, `date_added`) VALUES ('" + uuid + "', CURRENT_DATE());")

// Check result, do something
```
