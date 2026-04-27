plugins {
	id("net.fabricmc.fabric-loom")
	`maven-publish`
    id("me.modmuss50.mod-publish-plugin") version "0.8.+"
    id("co.uzzu.dotenv.gradle") version "4.0.0"

}

version = "${property("mod_version")}+${property("minecraft_version")}"
group = providers.gradleProperty("maven_group").get()

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
    maven {
        name = "NeoForge"
        url = uri("https://maven.neoforged.net/releases/")
        content {
            includeGroupAndSubgroups("net.neoforged")
            includeGroupAndSubgroups("cpw.mods")
            includeGroupAndSubgroups("net.minecraftforge")
        }
    }
}


loom {
    accessWidenerPath = file("src/main/resources/slime_time.classtweaker")
}

fabricApi {
    configureDataGeneration({
        client = true
        modId = "slime_time"
    })
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
	
	implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	implementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}")
    // NeoForge
    compileOnly ("net.neoforged:neoforge:${property("neoforge_version")}:universal")
    compileOnly("net.neoforged.fancymodloader:loader:${property("neoforge_loader_version")}")
    // Yumi MC Commons
    api("dev.yumi.mc.core:yumi-mc-foundation:${property("yumi_version")}")
    include("dev.yumi.mc.core:yumi-mc-foundation:${property("yumi_version")}")
    implementation("dev.yumi.mc.core:yumi-mc-foundation:${property("yumi_version")}")
    // Mod Menu
    compileOnly("maven.modrinth:modmenu:${property("modmenu_version")}")
    localRuntime("maven.modrinth:modmenu:${property("modmenu_version")}")

    // Configs
    implementation("folk.sisby:kaleido-config:${property("kaleido_version")}")
    include("folk.sisby:kaleido-config:${property("kaleido_version")}")

    // McQoy
    implementation("dev.isxander:yet-another-config-lib:${property("yacl_version")}")
    implementation("maven.modrinth:mcqoy:${property("mcqoy_version")}")

    // RRV
	compileOnly("cc.cassian.rrv:reliable-recipe-viewer-fabric:${property("rrv_version")}") {
        isTransitive = false
    }
    localRuntime("cc.cassian.rrv:reliable-recipe-viewer-fabric:${property("rrv_version")}") {
        isTransitive = false
    }
	
}

tasks.processResources {
    fun prop(name: String) = project.property(name) as String

    val props = HashMap<String, String>().apply {
        this["mod_version"] = prop("mod_version") + "+" + prop("minecraft_version")
        this["mod_name"] = prop("mod_name")
        this["mod_license"] = prop("mod_license")
        this["mod_id"] = prop("mod_id")
        this["mod_description"] = prop("mod_description")

    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml")) {
        expand(props)
    }
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 25
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
    val projectName = project.name
    inputs.property("projectName", projectName)

	from("LICENSE") {
		rename { "${it}_${project.name}" }
	}
}

// configure the maven publication
publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}

publishMods {
    file = tasks.jar.map { it.archiveFile.get() }
    additionalFiles.from(tasks.named<org.gradle.jvm.tasks.Jar>("sourcesJar").map { it.archiveFile.get() })

    // one of BETA, ALPHA, STABLE
    type = STABLE
    displayName = "${property("mod_name")} ${property("mod_version")} for ${property("minecraft_version")} Fabric"
    version = "${property("mod_version")}+${property("minecraft_version")}"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("fabric")
    modLoaders.add("neoforge")


    modrinth {
        projectId = property("modrinth_id") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(property("minecraft_version").toString())
        requires("fabric-api")
        optional("mcqoy")
        optional("rrv")
    }

    /*
    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(property("minecraft_version").toString())
        requires("fabric-api")
        optional("mcqoy")
        optional("rrv")
    }

     */
}