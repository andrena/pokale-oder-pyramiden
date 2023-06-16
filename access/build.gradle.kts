import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "de.andrena.access"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

val kotlinVersion = "1.8.10"
val springBootVersion = "3.0.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation(kotlin("gradle-plugin"))
	implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

	implementation("io.spring.gradle:dependency-management-plugin:1.1.0")
	implementation("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

	implementation("com.h2database:h2:2.1.214")

	testImplementation(kotlin("test"))
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.1") {
		exclude(module = "mockito-core")
	}
	testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
	testImplementation("org.assertj:assertj-core:3.24.1")
	testImplementation("io.mockk:mockk:1.13.4")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
