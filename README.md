# A simple JDBC wrapper

![Java CI with Gradle](https://github.com/Huskehhh/MySQL/workflows/Java%20CI%20with%20Gradle/badge.svg)

A simple, clean and effective JDBC wrapper built on top of [HikariCP](https://github.com/brettwooldridge/HikariCP)

## Setting up your project workspace

### Maven

To integrate this library in your project using maven, add these to your pom.xml

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.Huskehhh</groupId>
    <artifactId>MySQL</artifactId>
    <version>CHANGEME</version>
</dependency>
```          

### Gradle

Add this to repositories

```kotlin
maven {
    url = uri("https://jitpack.io")
}
```                  

And add this to dependencies

```kotlin
implementation("com.github.Huskehhh:MySQL:CHANGEME")
```

#### Note: it is assumed that mysql-connector-java is provided on the classpath.

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

```kotlin
implementation("mysql:mysql-connector-java:VERSION")
```

Versions can be found [here](https://mvnrepository.com/artifact/mysql/mysql-connector-java)

## Usage

### Instantiate the MySQL wrapper.

```java
MySQL mysql = new MySQL(url, username, password);
```

### Query

Sync & async functions are provided, depending on your use case.

#### Example sync query

```java
mysql.query("SELECT * from table WHERE id = 1;", results -> {
    if (results != null) {
        // Do something
    }
});
```

### Update

#### Example sync update

```java
int retval = mysql.update("INSERT INTO `whitelist` (`uuid`, `date_added`) VALUES ('"+uuid+"', CURRENT_DATE());")
```
