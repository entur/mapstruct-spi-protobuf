# Protobuf accessor naming strategy [![CircleCI](https://circleci.com/gh/entur/mapstruct-spi-protobuf.svg?style=svg)](https://circleci.com/gh/entur/mapstruct-spi-protobuf)

This naming strategy helps [mapstruct](http://mapstruct.org/) generate working mapping code between your domain classes and protobuf classes.
Both [fullblown Java protobuf](https://github.com/protocolbuffers/protobuf/tree/master/java) and [protolite](https://github.com/protocolbuffers/protobuf/blob/master/java/lite.md) classes suported.

Manual mapping needed for

* map<k,v>
* oneof


# Usage

## Maven

Add the following section to you maven-compiler-plugin plugin configuration:

```xml
<annotationProcessorPaths>
	<path>
		<groupId>no.entur.mapstruct.spi</groupId>
		<artifactId>protobuf-spi-impl</artifactId>
		<version>1.0.0-SNAPSHOT</version>
	</path>
</annotationProcessorPaths>
<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${org.mapstruct.version}</version>
    </dependency>
</dependencies>

```

Complete example:
```xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-compiler-plugin</artifactId>

	<configuration>
		<source>1.8</source> 
		<target>1.8</target> 
		<annotationProcessorPaths>
			<path>
        		<groupId>no.entur.mapstruct.spi</groupId>
		        <artifactId>protobuf-spi-impl</artifactId>
		        <version>1.0.0-SNAPSHOT</version>
			</path>
		</annotationProcessorPaths>
	</configuration>
    <dependencies>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${org.mapstruct.version}</version>
        </dependency>
    </dependencies>

</plugin>
```

## Gradle

Figure it out and create a pull request ;)

# More information:

http://mapstruct.org/documentation/stable/reference/html/index.html#using-spi
