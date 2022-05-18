package com.github.jmlb23.exampleapi.application

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {

    build()
        .args(*args)
        .packages("com.github.jmlb23.exampleapi")
        .start()

}
