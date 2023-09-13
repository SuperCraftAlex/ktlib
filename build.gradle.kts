plugins {
    kotlin("jvm") version "1.9.0"
    application
    `maven-publish`
}

group = "me.alex_s168"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.alex_s168"
            artifactId = "ktlib"
            version = "1.0.0"

            from(components["java"])
        }
    }
}