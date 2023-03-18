rootProject.name = "bookstore"

pluginManagement {
    val detektId: String by settings
    val detektVersion: String by settings
    val dokkaId: String by settings
    val dokkaVersion: String by settings
    val githubShadowId: String by settings
    val githubShadowVersion: String by settings
    val kotlinId: String by settings
    val kotlinVersion: String by settings
    val micronautId: String by settings
    val micronautVersion: String by settings
    val testLoggerId: String by settings
    val testLoggerVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace?.startsWith(kotlinId) == true)
                useVersion(kotlinVersion)

            if (requested.id.namespace?.startsWith(micronautId) == true)
                useVersion(micronautVersion)

            when (requested.id.id) {
                testLoggerId -> useVersion(testLoggerVersion)
                detektId -> useVersion(detektVersion)
                dokkaId -> useVersion(dokkaVersion)
                kotlinId -> useVersion(kotlinVersion)
                githubShadowId -> useVersion(githubShadowVersion)
            }
        }
    }
}

