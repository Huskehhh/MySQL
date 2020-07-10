# A simple JDBC wrapper

![Java CI with Gradle](https://github.com/Huskehhh/MySQL/workflows/Java%20CI%20with%20Gradle/badge.svg)

A simple, clean and effective JDBC wrapper built on top of [HikariCP](https://github.com/brettwooldridge/HikariCP)

## Setting up your project workspace

### Maven
To integrate this library in your project using maven, add these to your pom.xml
```xml
<repository>
    <id>husk</id>
    <url>https://maven.husk.pro/repository/maven-public/</url>
</repository>
```

```xml
<dependency>
    <groupId>pro.husk</groupId>
    <artifactId>mysql</artifactId>
    <version>1.4.0</version>
</dependency>
```          

### Gradle
Add this to repositories
```xml
maven {
    url = 'https://maven.husk.pro/repository/maven-public/'
}
```                  
And add this to dependencies
```xml
implementation 'pro.husk:mysql:1.4.0'
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
Alternatively, you can also just compile from source, [download a compiled version](https://github.com/Huskehhh/MySQL/actions) and add it to your classpath, or supply the files in your project workspace!

## Usage
### Create the database
```Java
// Create instance
MySQL mysql = new MySQL(url, username, password);
```
### Query

For queries, there's two different ways we can do it!

If we are just processing data, we can do it this way (so we don't have to clean up resources later! (Recommended))
#### Sync query
```Java
// Execute query
mysql.query("SELECT * from table WHERE id = 1;", results -> {
    if (results != null) {
      // do something
    }
});
```            
...or you can get the ResultSet itself through

```Java
    try (ResultSet results = mysql.query(query)) {
            // Do something with the ResultSet

            // Then close statement (the ResultSet will close itself)
            results.getStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
```
Please make sure you close resources, or you'll end up with a memory leak! D:

### Update

#### Sync update
```Java
int resultCode = mysql.update("INSERT INTO `whitelist` (`uuid`, `date_added`) VALUES ('" + uuid + "', CURRENT_DATE());")

// Check result, do something
```
