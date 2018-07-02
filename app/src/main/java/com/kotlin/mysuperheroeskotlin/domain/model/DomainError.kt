package com.kotlin.mysuperheroeskotlin.domain.model

// https://kotlinlang.org/docs/reference/sealed-classes.html
sealed class DomainError
object NotInternetDomainError : DomainError()
data class UnknownDomainError(val errorMessage: String = "Unknown Error") : DomainError()
data class NotIndexFoundDomainError(val key: String) : DomainError()
object AuthDomainError : DomainError()