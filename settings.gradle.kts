pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Android-Bootcamp"
include(":app")
include(":core:common")
include(":core:domain")
include(":core:data")
include(":core:ui")
include(":core:di")
include(":feature:intro")
include(":feature:splash")
include(":feature:login")
include(":feature:register")
include(":feature:read")
include(":feature:bookshelf_details")
include(":feature:bookshelf")
include(":feature:details")
include(":feature:home")
include(":feature:profile")
include(":feature:review")
include(":feature:search")
