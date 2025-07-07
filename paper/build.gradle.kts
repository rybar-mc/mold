plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.opencollab.dev/main")
}

dependencies {
    api(project(":common"))
    compileOnly("org.geysermc.floodgate:api:2.2.4-SNAPSHOT")

    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION