# A simple JDBC wrapper

[![Build Status](https://travis-ci.com/Huskehhh/MySQL.svg?branch=master)](https://travis-ci.com/Huskehhh/MySQL)

A simple, clean and effective JDBC wrapper built on top of [HikariCP](https://github.com/brettwooldridge/HikariCP)

## Setting up your project workspace

### Maven
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
    <version>1.3.1</version>
</dependency>
```          

### Gradle
Add this to repositories
```xml
maven {
    url = 'https://maven.husk.pro/repository/internal/'
}
```                  
And add this to dependencies
```xml
implementation 'pro.husk:mysql:1.3.1'
```

#### Note: it is assumed that mysql-connector-java is provided

If it is not, please also add

For Maven
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>VERSION</version>
</dependency>
```             
or for Gradle
```xml
implementation 'mysql:mysql-connector-java:VERSION'
```

Versions can be found [here](https://mvnrepository.com/artifact/mysql/mysql-connector-java)

#### What if I don't use a build tool
Alternatively, you can also just compile from source, [download a compiled version](https://ci.husk.pro/job/MySQL/) and add it to your classpath,  or supply the files in your project workspace!

## Usage
### Create the database
```Java
// Create database
MySQL mysql = new MySQL(host, port, database, username, password, params);
```
### Query

#### Sync query
```Java
// Execute query
mysql.query("SELECT * from table WHERE id = 1;", results -> {
    if (results != null) {
      // do something
    }
});
```            

### Update

#### Sync update
```Java
int resultCode = mysql.update("INSERT INTO `whitelist` (`uuid`, `date_added`) VALUES ('" + uuid + "', CURRENT_DATE());")

// Check result, do something
```