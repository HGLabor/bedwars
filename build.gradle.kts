plugins {
    java
    kotlin("jvm") version "1.5.21"
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
    implementation("net.axay", "kspigot", "1.17.2")
    compileOnly("org.spigotmc", "spigot", "1.17.1-R0.1-SNAPSHOT")
    implementation("de.hglabor:hglabor-utils:0.0.10")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.6-SNAPSHOT")
}

java.sourceCompatibility = JavaVersion.VERSION_16
java.targetCompatibility = JavaVersion.VERSION_16

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}