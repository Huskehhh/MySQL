## A MySQL Library for Java

####To integrate this library in your project, add these to your pom.xml
```
<repository>
    <id>mysql</id>
    <url>https://husk.pro/maven/</url>
</repository>
```

```
<dependency>
    <groupId>pro.husk</groupId>
    <artifactId>mysql</artifactId>
    <version>1.0</version>
</dependency>
```

This will allow you to then import the required classes!

Please note, it is assumed that mysql-connector-java is provided

If it is not, please also add

```
<dependency>
       <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
     <version>VERSION</version>
</dependency>
```

Version can be found from 
https://mvnrepository.com/artifact/mysql/mysql-connector-java

####Alternatively, you can also just supply these classes in your local project workspace