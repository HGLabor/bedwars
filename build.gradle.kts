plugins {
    java
    kotlin("jvm") version "1.5.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "de.hglabor"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("org.spigotmc", "spigot-api", "1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol", "ProtocolLib", "4.6.0")
    implementation("de.hglabor:hglabor-utils:0.0.10")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.0-SNAPSHOT")
    compileOnly("org.bukkit","craftbukkit","1.16.5-R0.1-SNAPSHOT")
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}