dependencies {
    // project
    implementation(project(":common:types"))
    implementation(project(":shop:domain"))
    implementation(project(":shop:usecase"))
    implementation(project(":shop:query"))

    // kotlin
    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.kotlin_stdlib)
    implementation(Libs.arrow)

    // test
    testImplementation(Libs.kotest_junit)
    testImplementation(Libs.kotest_arrow)
    testImplementation(Libs.junit_engine)
    testImplementation(Libs.junit_params)
}