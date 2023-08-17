package com.ieee.codelink.common.event

fun <T> Event<T>.getIfNotHandled(action: (value: T) -> Unit) {
    this.getContentIfNotHandled()?.let { value -> action(value) }
}

fun Event<String>.getIfNotHandledOrEmpty(action: (value: String) -> Unit) {
    this.getContentIfNotHandled()?.let { value ->
        if (value.isNotBlank()) action(value)
    }
}