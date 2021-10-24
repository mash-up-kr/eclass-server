package com.mashup.eclassserver.exception

class EclassException(val errorCode: ErrorCode, val errorMessage: String, val throwable: Throwable?) : RuntimeException(errorMessage, throwable) {
    constructor(errorCode: ErrorCode) : this(errorCode, errorCode.description, null)
    constructor(errorCode: ErrorCode, message: String) : this(errorCode, message, null)
}