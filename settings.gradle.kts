rootProject.name = "mold"

include("common")

// Platforms
include("paper")
include("bridge")

// Test
include("test:paper")
project(":test:paper").name = "paper-test"