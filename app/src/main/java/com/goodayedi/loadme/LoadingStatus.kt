package com.goodayedi.loadme

enum class LoadingStatus(val label: Int) {
    NORMAL(R.string.download),
    LOADING(R.string.we_are_downloading),
    LOADED(R.string.content_loaded);

    fun next() = when(this) {
        NORMAL -> LOADING
        LOADING -> LOADED
        LOADED -> NORMAL
    }
}