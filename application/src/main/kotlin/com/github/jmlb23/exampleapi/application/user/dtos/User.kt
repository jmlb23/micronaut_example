package com.github.jmlb23.exampleapi.application.user.dtos

import com.github.jmlb23.exampleapi.domain.bo.User

data class UserDTO(val name: String, val surname: String, val email: String) {
    companion object {
        fun User.toDTO(): UserDTO = UserDTO(name.name, surname.name, email.email)
    }
}