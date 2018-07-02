package com.kotlin.mysuperheroeskotlin.ui.view

import android.content.Context
import com.kotlin.mysuperheroeskotlin.R
import com.kotlin.mysuperheroeskotlin.domain.model.*

fun DomainError.asString(context: Context): String =
        context.getString(getMessage(this))

fun getMessage(domainError: DomainError): Int =
        when (domainError) {
            is NotInternetDomainError -> R.string.error_not_internet_message
            is NotIndexFoundDomainError -> R.string.error_superhero_not_found_message
            is AuthDomainError -> R.string.error_invalid_credentials
            is UnknownDomainError -> R.string.error_unknown_message
        }