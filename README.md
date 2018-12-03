# Protobuf accessor naming strategy [![CircleCI](https://circleci.com/gh/entur/mapstruct-spi-protobuf.svg?style=svg)](https://circleci.com/gh/entur/mapstruct-spi-protobuf)

This naming strategy helps [mapstruct](http://mapstruct.org/) generate working mapping code between your domain classes and protobuf classes.

# Usage

## Maven

In the Maven compiler plugin, add the following section to you maven-compiler-plugin plugin configuration:

```xml
<annotationProcessorPaths>
	<path>
		<groupId>no.entur</groupId>
		<artifactId>mapstruct-spi-protobuf</artifactId>
		<version>1.0-SNAPSHOT</version>
	</path>
</annotationProcessorPaths>
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
				<groupId>no.entur</groupId>
				<artifactId>mapstruct-spi-protobuf</artifactId>
				<version>1.0-SNAPSHOT</version>
			</path>
		</annotationProcessorPaths>
	</configuration>
</plugin>
```

## Gradle

Figure it out and create a pull request ;)

# More information:

http://mapstruct.org/documentation/stable/reference/html/index.html#using-spi
