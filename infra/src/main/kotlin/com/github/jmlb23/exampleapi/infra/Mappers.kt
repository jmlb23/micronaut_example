package com.github.jmlb23.exampleapi.infra

import com.github.jmlb23.exampleapi.domain.functional.Either
import comgithubjmlb23exampleapiinfra.User
import com.github.jmlb23.exampleapi.domain.bo.User as UserBO
import com.github.jmlb23.exampleapi.domain.bo.Group as GroupBO

fun User.toBO(group: GroupBO): Either<com.github.jmlb23.exampleapi.domain.bo.ErrorBO, UserBO> {
    return UserBO(name, surname, mail, group.name, role_id == 1L)
}

