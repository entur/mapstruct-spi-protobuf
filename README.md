# Mapstruct SPI implementation for protocol buffers [![CircleCI](https://circleci.com/gh/entur/mapstruct-spi-protobuf.svg?style=svg)](https://circleci.com/gh/entur/mapstruct-spi-protobuf)

This naming strategy helps [mapstruct](http://mapstruct.org/) generate working mapping code between your domain classes
and protobuf classes. Both [fullblown Java protobuf](https://github.com/protocolbuffers/protobuf/tree/master/java)
and [protolite](https://github.com/protocolbuffers/protobuf/blob/master/java/lite.md) classes suported.

Requires on mapstruct 1.4.0+.

## ProtobufAccessorNamingStrategy

Extends ```DefaultProtobufAccessorNamingStrategy``` and provides necessary information to map all fields automatically *
except*

* map<k,v>
* oneof

which require manual mapping.

## ProtobufEnumMappingStrategy

Implements ```EnumMappingStrategy``` and provides complete enum constant mappings if you follow Googles style guide for
enums https://developers.google.com/protocol-buffers/docs/style#enums

If needed you can specify a different postfix for the 0 value enum by passing in `mapstructSpi.enumPostfixOverrides` as 
a compilerArg in the format of:

`-AmapstructSpi.enumPostfixOverrides=com.package.root.a=POSTFIX_1,com.package.root.b=POSTFIX_2`

Otherwise, this will default to `UNSPECIFIED` as per the Google style guide.

```xml

<plugin>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>no.entur.mapstruct.spi</groupId>
                <artifactId>protobuf-spi-impl</artifactId>
                <version>LATEST.VERSION</version>
            </path>
        </annotationProcessorPaths>
        <compilerArgs>
            <arg>-AmapstructSpi.enumPostfixOverrides=com.company.name=INVALID</arg>
        </compilerArgs>
    </configuration>
</plugin>

```


## Support - Mapping funcions:

Standard mapping functions between often used proto types and java types:

* Timestamp <-> Instant
* Duration <-> Duration
* Date <-> LocalDate
* TimeOfDay <-> LocalTime
* byte[] <-> ByteString

See [protobuf-support-standard](support-standard) and/or [protobuf-support-lite](support-lite) folders for a ready-to-use mapstruct mapper.

# Usage

[See example project](usage/)

NB: Make sure you add `collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED` to your mapping interfaces as protobuf stubs use the builder pattern. 
```
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ... {
```

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
implementation"org.mapstruct:mapstruct:${mapstructVersion}"
        annotationProcessor"org.mapstruct:mapstruct-processor:${mapstructVersion}"
        annotationProcessor"no.entur.mapstruct.spi:protobuf-spi-impl:LATEST.VERSION"
```

# More information:

http://mapstruct.org/documentation/stable/reference/html/index.html#using-spi


