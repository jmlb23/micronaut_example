package com.github.jmlb23.exampleapi.application.user.dtos

import com.github.jmlb23.exampleapi.domain.bo.EmailErrorBO
import com.github.jmlb23.exampleapi.domain.bo.ErrorBO
import com.github.jmlb23.exampleapi.domain.bo.RepositoryErrorBO
import com.github.jmlb23.exampleapi.domain.bo.StringErrorBO
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus

sealed class Response<out E, out S> {
    data class SuccessResponse<B>(val code: Int, val data: B) : Response<Nothing, B>()
    data class ErrorResponse(val code: Int, val data: String) : Response<String, Nothing>()
}

fun ErrorBO.toErrorResponse(): Response.ErrorResponse = when (this) {
    EmailErrorBO.EmptyEmailErrorBO -> Response.ErrorResponse(400, "Email not valid")
    EmailErrorBO.NotValidEmailErrorBO -> Response.ErrorResponse(400, "Email not valid")
    RepositoryErrorBO.AddErrorBO -> Response.ErrorResponse(500, "Internal Error")
    RepositoryErrorBO.ConnectionErrorBO -> Response.ErrorResponse(500, "Internal Error")
    RepositoryErrorBO.NotFoundErrorBO -> Response.ErrorResponse(404, "Not Found")
    StringErrorBO.EmptyStringErrorBO -> Response.ErrorResponse(400, "Empty field")
}

fun <E, S> Response<S, E>.toHttp(): HttpResponse<Response<S, E>> =
    when (this) {
        is Response.ErrorResponse -> HttpResponse.status<Response.ErrorResponse>(HttpStatus.valueOf(this.code))
            .body(this)
        is Response.SuccessResponse -> HttpResponse.status<Response.SuccessResponse<S>>(HttpStatus.valueOf(this.code))
            .body(this)
    }
