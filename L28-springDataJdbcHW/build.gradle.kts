plugins {
    id ("org.springframework.boot")
}


dependencies {
    dependencies {
        implementation ("ch.qos.logback:logback-classic")
        implementation ("org.flywaydb:flyway-core")
        implementation ("org.postgresql:postgresql")
        implementation ("com.google.code.findbugs:jsr305")
        implementation ("org.springframework.boot:spring-boot-starter-data-jdbc")

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("org.springframework.boot:spring-boot-starter-test")
        runtimeOnly("org.flywaydb:flyway-database-postgresql")

        compileOnly ("org.projectlombok:lombok")
        annotationProcessor ("org.projectlombok:lombok")
    }
}