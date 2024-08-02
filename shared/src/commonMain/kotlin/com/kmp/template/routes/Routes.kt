package com.kmp.template.routes

import com.kmp.template.Config

enum class Routes(val value: String, val params: Any) {
    MY_ROUTE("my_route", MyRouteParams());

    fun fullRoute(): String {
        return "${Config.backendUrl}/$value"
    }

    @Throws(TypeCastException::class)
    inline fun < reified T : Any> params(): T {
        if (params is T) {
            return params
        }
        throw TypeCastException("Params $params are not proper type");
    }
}

data class MyRouteParams (
     val myParamName: String = "myParamName"
)