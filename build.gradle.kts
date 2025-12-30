plugins {
    application
}

group = "org.aseccxz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("xyz.froud:JVisa:2.0.0")
    implementation("net.java.dev.jna:jna:5.18.1")
    implementation("net.java.dev.jna:jna-platform:5.18.1")
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass = "org.aseccxz.Main"  // полное имя класса с пакетом
}