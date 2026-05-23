@file:Suppress("UnstableApiUsage")

plugins {
    id("net.fabricmc.fabric-loom")
    id("dev.kikugie.postprocess.jsonlang")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
}

val minecraft = stonecutter.current.version
val mcVersion = stonecutter.current.project.substringBeforeLast('-')

tasks.named<ProcessResources>("processResources") {
    fun prop(name: String) = project.property(name) as String

    val props = HashMap<String, String>().apply {
        this["mod_version"] = prop("mod.version") + "+" + prop("deps.minecraft")
        this["minecraft_version"] = prop("deps.minecraft_dependency")
        this["mod_name"] = prop("mod.name")
        this["mod_license"] = prop("mod.license")
        this["mod_id"] = prop("mod.id")
        this["mod_description"] = prop("mod.description")
    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }

}

tasks.named("processResources") {
    dependsOn(":${stonecutter.current.project}:stonecutterGenerate")
}

version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
base.archivesName = property("mod.id") as String


loom {
    accessWidenerPath = rootProject.file("src/main/resources/slime_time.classtweaker")
    runs.configureEach {
        isIdeConfigGenerated = true
    }
}

fabricApi {
    configureDataGeneration({
        client = true
        modId = "slime_time"
        outputDirectory = rootProject.file("src/main/generated")
    })
}

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    maven {
        name = "Terraformers (Mod Menu)"
        url = uri("https://maven.terraformersmc.com/releases/")
        content {
            includeGroupAndSubgroups("com.terraformersmc")
        }
    }
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroupAndSubgroups("maven.modrinth")
        }
    }
    maven {
        name = "Sisby Maven"
        url = uri("https://repo.sleeping.town/")
        content {
            includeGroupAndSubgroups("folk.sisby")
        }
    }
    maven {
        name = "Xander Maven"
        url = uri("https://maven.isxander.dev/releases")
        content {
            includeGroupAndSubgroups("dev.isxander")
            includeGroupAndSubgroups("org.quiltmc.parsers")
        }
    }
    maven {
        name = "Cassian's Maven"
        url = uri("https://maven.cassian.cc")
        content {
            includeGroupAndSubgroups("cc.cassian")
        }
    }
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")

    implementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    implementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
    // Mod Menu
    compileOnly("maven.modrinth:modmenu:${property("deps.modmenu")}")

    // Configs
    implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    include("folk.sisby:kaleido-config:${property("deps.kaleido")}")

    // McQoy
    compileOnly("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-fabric")

    // RRV
    compileOnly("cc.cassian.rrv:reliable-recipe-viewer-fabric:${property("deps.rrv")}") {
        isTransitive = false
    }
    localRuntime("cc.cassian.rrv:reliable-recipe-viewer-fabric:${property("deps.rrv")}") {
        isTransitive = false
    }
    localRuntime("cc.cassian.item-descriptions:item-descriptions-fabric:${property("deps.item_descriptions")}") {
        exclude(group = "mcp.mobius.waila")
        exclude(group = "lol.bai")
    }
    if (stonecutter.eval(mcVersion, "=26.1")) {
        localRuntime("maven.modrinth:modmenu:${property("deps.modmenu")}")
        implementation("maven.modrinth:mcqoy:${property("deps.mcqoy")}")
        localRuntime("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-fabric")
    }

}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    }
}

stonecutter {
    replacements.string {
        direction = eval(current.version, ">1.21.10")
        replace("ResourceLocation", "Identifier")
    }
    replacements.string {
        direction = eval(current.version, ">26.1")
        replace("EntityType.SLIME,", "EntityTypes.SLIME,")
    }
    replacements.string {
        direction = eval(current.version, ">26.1")
        replace("EntityType.MAGMA_CUBE", "EntityTypes.MAGMA_CUBE")
    }
    replacements.string {
        direction = eval(current.version, ">26.1")
        replace("net.minecraft.world.entity.monster.Slime", "net.minecraft.world.entity.monster.cubemob.Slime")
    }
    replacements.string {
        direction = eval(current.version, ">26.1")
        replace("net.minecraft.world.entity.monster.MagmaCube", "net.minecraft.world.entity.monster.cubemob.MagmaCube")
    }
}

tasks {
    processResources {
        exclude("**/neoforge.mods.toml", "**/interfaces.json", "**/mods.toml")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
val additionalVersions: List<String> = additionalVersionsStr
    ?.split(",")
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() }
    ?: emptyList()

publishMods {
    file = tasks.jar.map { it.archiveFile.get() }

    // one of BETA, ALPHA, STABLE
    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Fabric"
    version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("fabric")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(property("deps.minecraft").toString())
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
        optional("modmenu")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(property("deps.minecraft").toString())
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
    }
}
