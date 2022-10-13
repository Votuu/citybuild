plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://libraries.minecraft.net/")
    }
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4")
    implementation("org.mongodb:mongodb-driver-sync:4.7.1")
    implementation("dev.morphia.morphia:morphia-core:2.2.9")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.code.gson:gson:2.7")
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("com.mojang:brigadier:1.0.18")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}