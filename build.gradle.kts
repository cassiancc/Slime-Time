plugins {
	id("net.fabricmc.fabric-loom")
	`maven-publish`
    id("me.modmuss50.mod-publish-plugin") version "0.8.+"
    id("co.uzzu.dotenv.gradle") version "4.0.0"

}

version = "${property("mod_version")}+${property("minecraft_version")}-fabric"
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
}


loom {
    accessWidenerPath = file("src/main/resources/springythings.classtweaker")
}

fabricApi {
    configureDataGeneration({
        client = true
        modId = "springythings"
    })
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
	
	implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	implementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}")

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
	inputs.property("version", version)

	filesMatching("fabric.mod.json") {
		expand("version" to version)
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
	inputs.property("projectName", project.name)

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
    version = "${property("mod_version")}+${property("minecraft_version")}-fabric"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("fabric")


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