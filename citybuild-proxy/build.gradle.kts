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
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.code.gson:gson:2.7")

    compileOnly("com.velocitypowered:velocity-api:3.1.1")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}