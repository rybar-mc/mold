plugins {
    id("java-library")
}

allprojects {
    apply(plugin = "java-library")

    group = "org.rybar.mold"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:26.0.2")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
    }
}