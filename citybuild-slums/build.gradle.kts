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
    implementation("dev.morphia.morphia:morphia-core:2.2.9")
    compileOnly(project(":citybuild-core"))
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}