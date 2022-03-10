package com.splanes.weektasks.ui.feature.newtaskform.common.model

import androidx.annotation.StringRes

sealed class FormUiModel<Field : FormField>(
    open val id: Field,
    open val value: Any? = null,
    open val validators: List<FormValidator> = emptyList(),
    @StringRes open val placeholder: Int? = null
) {
    data class Idle<Field : FormField>(
        override val id: Field,
        override val value: Any? = null,
        override val validators: List<FormValidator> = emptyList(),
        @StringRes override val placeholder: Int? = null
    ) : FormUiModel<Field>(
        id,
        value,
        validators,
        placeholder
    )

    data class Error<Field : FormField>(
        override val id: Field,
        override val value: Any? = null,
        override val validators: List<FormValidator> = emptyList(),
        @StringRes override val placeholder: Int? = null,
        @StringRes val errorMessage: Int? = null
    ) : FormUiModel<Field>(
        id,
        value,
        validators,
        placeholder
    )

    fun update(value: Any?): FormUiModel<Field> {
        val failed = validators.find { validator -> !validator.isValid(value) }
        return when (this) {
            is Error -> if (failed != null) {
                copy(value = value, errorMessage = failed.error)
            } else {
                Idle(id = id, value = value, validators = validators, placeholder = placeholder)
            }
            is Idle -> if (failed != null) {
                Error(
                    id = id,
                    value = value,
                    validators = validators,
                    placeholder = placeholder,
                    errorMessage = failed.error
                )
            } else {
                copy(value = value)
            }
        }
    }
}
