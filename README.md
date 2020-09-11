# Mapstruct SPI implementation for protocol buffers [![CircleCI](https://circleci.com/gh/entur/mapstruct-spi-protobuf.svg?style=svg)](https://circleci.com/gh/entur/mapstruct-spi-protobuf)

This naming strategy helps [mapstruct](http://mapstruct.org/) generate working mapping code between your domain classes and protobuf classes.
Both [fullblown Java protobuf](https://github.com/protocolbuffers/protobuf/tree/master/java) and [protolite](https://github.com/protocolbuffers/protobuf/blob/master/java/lite.md) classes suported.

NOTE: Depends on mapstruct 1.4.0.CR1

## ProtobufAccessorNamingStrategy

Extends ```DefaultProtobufAccessorNamingStrategy``` and provides necessary information to map all fields automatically *except* 

* map<k,v>
* oneof

which require manual mapping.

## ProtobufEnumMappingStrategy

Implements ```EnumMappingStrategy``` and provides complete enum constant mappings if you follow Googles style guide for enums https://developers.google.com/protocol-buffers/docs/style#enums

# Usage

[See example project](usage/)

## Maven

Add the following section to you maven-compiler-plugin plugin configuration:

```xml
<annotationProcessorPaths>
	<path>
		<groupId>no.entur.mapstruct.spi</groupId>
		<artifactId>protobuf-spi-impl</artifactId>
		<version>LATEST.VERSION</version>
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
		        <version>LATEST.VERSION</version>
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

```java
implementation "org.mapstruct:mapstruct:${mapstructVersion}"
annotationProcessor "org.mapstruct:mapstruct-processor:${mapstructVersion}"
annotationProcessor "no.entur.mapstruct.spi:protobuf-spi-impl:LATEST.VERSION"
```

# More information:

http://mapstruct.org/documentation/stable/reference/html/index.html#using-spi

