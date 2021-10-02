package com.mashup.eclassserver.testutils

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors.*


class ApiDocumentUtils {
    fun getDocumentRequest(): OperationRequestPreprocessor? {
        return preprocessRequest(
            modifyUris()
                    .scheme("https")
                    .host("server.jonghyeon.com")
                    .removePort(),
            prettyPrint()
        )
    }

    fun getDocumentResponse(): OperationResponsePreprocessor? {
        return preprocessResponse(prettyPrint())
    }
}