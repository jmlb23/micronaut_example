package com.github.jmlb23.exampleapi.domain.bo

sealed class ErrorBO

sealed class StringErrorBO : ErrorBO() {
    object EmptyStringErrorBO : StringErrorBO()
}

sealed class EmailErrorBO : ErrorBO() {
    object EmptyEmailErrorBO : EmailErrorBO()
    object NotValidEmailErrorBO : EmailErrorBO()
}

sealed class RepositoryErrorBO : ErrorBO(){
    object NotFoundErrorBO : RepositoryErrorBO()
    object AddErrorBO : RepositoryErrorBO()
    object ConnectionErrorBO : RepositoryErrorBO()
}