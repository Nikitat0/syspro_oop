plugins {
    application
    java
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass = "ru.nikitat0.expressions.Main"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
    }
}
