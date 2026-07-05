# TDLib JNA Core (Pre-compiled for Java)

A ready-to-use, zero-setup Telegram Database Library (TDLib) wrapper for Java using JNA. 
No more C++ compilation nightmares, no abandoned JNI wrappers, just pure JSON communication.

## Why does this exist?

If you've ever tried to build a Telegram Userbot in Java, you know the pain:
1. Compiling TDLib from source takes ~75 minutes and requires up to 15GB of disk space.
2. Existing Java wrappers (like `tdlight`) are mostly abandoned.
3. Strict JNI bindings crash constantly due to `GIT_COMMIT_HASH` mismatch or specific JVM versions.

**This library solves all of that.** We stripped the complexity, removed the strict commit hash validations, compiled `libtdjson.so` manually, and packed it directly into the `.jar` file. 

Thanks to **JNA**, you don't even need to configure native library paths. It automatically extracts and loads the `.so` file from the resources at runtime. Just plug and play.

## Features & Specs
* **Communication:** Pure JSON interface (JNA).
* **Environment:** Exclusively for **Linux x86_64** (Perfect for Docker, WSL2, Ubuntu servers).
* **Java Version:** Java 21+
* **TDLib Version:** 1.8.65 (Stable, bypasses the `406 UPDATE_APP_TO_LOGIN` error).
* **Footprint:** ~47MB pre-compiled binary inside.

## Installation

You can easily include this project using [JitPack](https://jitpack.io).

**Step 1.** Add the JitPack repository to your `pom.xml`:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

Step 2. Add the dependency (Replace Tag with the latest GitHub release or commit hash):
code Xml

<dependency>
    <groupId>com.github.Shredrik21</groupId>
    <artifactId>tdlib-jna-core</artifactId>
    <version>Tag</version>
</dependency>

## Quick Start

Once imported, the TdJson interface is available globally. Here is a minimal example of how to initialize the client:
code Java

import com.external.TdJson;
import com.sun.jna.Pointer;

public class Main {
    public static void main(String[] args) {
        // 1. Create client instance
        Pointer client = TdJson.INSTANCE.td_json_client_create();

        // 2. Disable noisy TDLib logs (0 = fatal, 1 = errors only)
        TdJson.INSTANCE.td_json_client_execute(null, "{\"@type\":\"setLogVerbosityLevel\",\"new_verbosity_level\":1}");

        // 3. Send a test query (e.g., getting current authorization state)
        TdJson.INSTANCE.td_json_client_send(client, "{\"@type\":\"getAuthorizationState\"}");

        // 4. Start Event Loop to receive updates
        while (true) {
            String result = TdJson.INSTANCE.td_json_client_receive(client, 1.0);
            if (result != null) {
                System.out.println("Received: " + result);
            }
        }
    }
}