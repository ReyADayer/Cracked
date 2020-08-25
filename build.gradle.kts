plugins {
    java
    kotlin("jvm").version(Dependencies.Kotlin.version)
    kotlin("kapt").version(Dependencies.Kotlin.version)
}

group = "neo.atlantis"
version = "0.0.1"
val pluginName = "Cracked"

repositories {
    jcenter()
    mavenCentral()
    maven(Dependencies.Spigot.repository)
    maven(Dependencies.SonaType.repository)
}

dependencies {
    compileOnly(Dependencies.Spigot.api)
    compileOnly(Dependencies.Spigot.annotations)
    kapt(Dependencies.Spigot.annotations)
    compile(Dependencies.Kotlin.stdlib)
    compile(Dependencies.Kotlin.reflect)
    compile(Dependencies.Rx.java)
    testCompile(Dependencies.JUnit.core)
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Kotlin.classpath)
    }
}

tasks {
    withType<Jar> {
        from(configurations.getByName("compile").map { if (it.isDirectory) it else zipTree(it) })
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}