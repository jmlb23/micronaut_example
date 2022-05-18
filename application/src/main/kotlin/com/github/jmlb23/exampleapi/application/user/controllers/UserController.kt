package com.github.jmlb23.exampleapi.application.user.controllers

import com.github.jmlb23.exampleapi.application.user.dtos.Response
import com.github.jmlb23.exampleapi.application.user.dtos.UserDTO
import com.github.jmlb23.exampleapi.application.user.dtos.UserDTO.Companion.toDTO
import com.github.jmlb23.exampleapi.application.user.dtos.toErrorResponse
import com.github.jmlb23.exampleapi.application.user.dtos.toHttp
import com.github.jmlb23.exampleapi.domain.bo.NonEmptyString
import com.github.jmlb23.exampleapi.domain.bo.User
import com.github.jmlb23.exampleapi.domain.functional.Either.Companion.flatMap
import com.github.jmlb23.exampleapi.domain.services.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject
import org.slf4j.Logger

@Controller("/user")
@Produces("application/json")
@Secured(SecurityRule.IS_AUTHENTICATED)
class UserController(@Inject val userService: UserService, val logger: Logger) {
    @Post
    suspend fun addUser(@Body user: UserDTO): HttpResponse<Response<String, Int>> {
        logger.info(user.toString())
        return User(user.name, user.surname, user.email)
            .flatMap { userService.addUser(it) }
            .fold({ it.toErrorResponse().toHttp() }, {
                Response.SuccessResponse(200, it).toHttp()
            })
    }

    @Get("/{domain}")
    suspend fun getUsers(domain: String): HttpResponse<Response<String, List<UserDTO>>> {
        logger.info(domain)
        return NonEmptyString(domain)
            .flatMap { userService.getGroup(it) }
            .fold({ it.toErrorResponse().toHttp() }, {
                Response.SuccessResponse(200, it.map { it.toDTO() }).toHttp()
            })

    }

}