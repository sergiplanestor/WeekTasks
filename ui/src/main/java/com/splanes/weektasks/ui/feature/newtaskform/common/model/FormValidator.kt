package com.splanes.weektasks.ui.feature.newtaskform.common.model

import androidx.annotation.StringRes
import com.splanes.weektasks.ui.R.string as Strings

sealed class FormValidator(
    open val isValid: (value: Any?) -> Boolean,
    @StringRes open val error: Int?
) {
    data class NotEmpty(@StringRes override val error: Int? = Strings.form_error_mandatory) : FormValidator(
        isValid = { it != null && it.toString().isNotBlank() },
        error = error
    )
}
