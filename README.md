fin-id-utils - validate Finnish personal identity codes
=======================================================

This library provides tools for Finnish personal identity codes HETU (henkilötunnus) ans SATU (sähköinen asiointitunnus).

Features

* Validate HETU and SATU identity codes.
* Check gender of a HETU code.
* Generate random codes.

See https://dvv.fi/en/personal-identity-code

## Add dependency

Apache Maven

    <dependency>
      <groupId>com.github.mpolla</groupId>
      <artifactId>fin-id-utils</artifactId>
      <version>0.5.2</version>
    </dependency>

Gradle Kotlin DSL

    implementation("com.github.mpolla:fin-id-utils:0.5.2")

Gradle Groovy DSL

    implementation 'com.github.mpolla:fin-id-utils:0.5.2'

## Usage (Java)

Validate a HETU/SATU code.

```java
boolean validHetu = HetuUtil.isValid("131052+308T");
boolean validSatu = SatuUtil.isValid("10011187H");
```

Check the gender of a HETU code.

```java
boolean isFemale = HetuUtil.isFemale("131052-308T");
```

Generate random HETU/SATU code.

```java
String randomHetu = HetuUtil.generateRandom();
String randomSatu = SatuUtil.generateRandom();
```
    
Compute the control character suffix for a HETU code.

```java
Char controlCharacter = HetuUtil.computeControlCharacter("131052-308");
```

## Usage (Kotlin)

Validate a HETU/SATU code.

```kotlin
val validHetu = HetuUtil.isValid("131052+308T")
val validSatu = SatuUtil.isValid("10011187H")
```

Check the gender of a HETU code.

```kotlin
val isFemale = HetuUtil.isFemale("131052-308T")
```

Generate random HETU/SATU code.

```kotlin
val randomHetu = HetuUtil.generateRandom()
val randomSatu = SatuUtil.generateRandom()
```
    
Compute the control character suffix for a HETU code.

```kotlin
val controlCharacter = HetuUtil.computeControlCharacter("131052-308")
```

## Development

Publish new release

    git tag x.x
    ./gradlew publish

![CI](https://github.com/mpolla/fin-id-utils/workflows/CI/badge.svg)
