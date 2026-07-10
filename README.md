# TDLib JNA Core

A multi-module cross-platform native library wrapper for Telegram Database Library (TDLib) using Java Native Access (JNA).

## Overview

This repository provides pre-compiled, statically linked native binaries of TDLib (compiled from the master branch) packaged as modular Maven dependencies via JitPack. 
By leveraging JNA and a modular project structure, this library eliminates the need for local C++ compilation, JNI header generation, or manual native library path configurations.

## Modular Architecture

To minimize the final application footprint and prevent the downloading of redundant native binaries, the project is structured as a Maven multi-module repository. 

The library is split into two types of modules:
1.  **The Core Module (`tdlib-jna-core`):** Contains only the Java JNA interfaces, DTOs, and signatures (~15 KB). It contains no native binaries.
2.  **Platform Modules (`tdlib-jna-<platform>`):** Each module contains only the native binary (`.so`, `.dll`, or `.dylib`) compiled for that specific platform.

### Dependency Resolution Rule:
You do not need to import the core module manually. When you import any platform-specific module, Maven or Gradle will automatically resolve the dependency tree and pull the `tdlib-jna-core` module transitively. 

For example, if you run a Spring Boot application on a Linux x86_64 server, declaring only the `tdlib-jna-linux-x86-64` dependency is sufficient. Your project will download exactly one native binary (`libtdjson.so` for Linux) and the Java core, completely ignoring the Windows, macOS, and Android binaries.

## Supported Modules

| Module Artifact ID | Target OS | CPU Architecture | Native Binary format |
|--------------------|-----------|------------------|----------------------|
| `tdlib-jna-core` | Common Java | N/A | None (Java interfaces only) |
| `tdlib-jna-linux-x86-64` | Linux | x86_64 | `libtdjson.so` |
| `tdlib-jna-linux-aarch64` | Linux | ARM64 (Aarch64) | `libtdjson.so` |
| `tdlib-jna-win32-x86-64` | Windows | x86_64 | `tdjson.dll` (Static Monolith) |
| `tdlib-jna-android` | Android | ARM64 / x86_64 | `libtdjson.so` (Multi-ABI) |
| `tdlib-jna-darwin-aarch64` | macOS | ARM64 (M1/M2/M3/M4) | `libtdjson.dylib` (Static Monolith) |

## Installation

The artifacts are published through JitPack. Replace the `VERSION` placeholder with the desired release tag (e.g., `1.0.2`).

### 1. Apache Maven

#### Add the repository definition to your `pom.xml`:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

#### Declare the platform dependency required for your runtime environment:
```xml
<dependency>
    <groupId>com.github.Shredrik21</groupId>
    <artifactId>tdlib-jna-linux-x86-64</artifactId>
    <version>VERSION</version>
</dependency>
```

### 2. Gradle (Kotlin DSL)

#### Add the repository to settings.gradle.kts:
```xml
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```
#### Declare the dependency in build.gradle.kts:
```xml
dependencies {
    implementation("com.github.Shredrik21:tdlib-jna-android:VERSION")
}
```
### 3. Gradle (Groovy DSL)

#### Add the repository to build.gradle:
```xml
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

#### Declare the dependency:
```xml
dependencies {
    implementation 'com.github.Shredrik21:tdlib-jna-android:VERSION'
}
```

## Technical Implementation Details

Static Linking: The Windows (tdjson.dll) and macOS (libtdjson.dylib) binaries are compiled as statically linked monoliths using CMake and vcpkg toolchains. They do not depend on external OpenSSL or Zlib installations.

JNA Resolution: The library utilizes the standard JNA resource loading convention. Native binaries are located inside the JARs under platform-specific directories (linux-x86-64/, win32-x86-64/, android-aarch64/, etc.) and are extracted and loaded automatically at runtime.










