plugins {
	kotlin("jvm") version "2.0.21" apply false
	kotlin("plugin.spring") version "2.0.21" apply false
	kotlin("plugin.jpa") version "2.0.21" apply false
	id("org.springframework.boot") version "3.3.4" apply false
	id("io.spring.dependency-management") version "1.1.6" apply false
}

allprojects {
	group = "com.todoapp"
	version = "1.0.0"

	repositories {
		mavenCentral()
	}
}