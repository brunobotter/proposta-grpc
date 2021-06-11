package br.com.bruno.erros.handler

import br.com.bruno.erros.exception.DocumentoJaCadastradoException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class DocumentoJaCadastradoExceptionHandler : ExceptionHandler<DocumentoJaCadastradoException>{

    override fun handle(e: DocumentoJaCadastradoException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.ALREADY_EXISTS
                .withDescription(e.message)
                .withCause(e))
    }

    override fun supports(e: Exception): Boolean {
        return e is DocumentoJaCadastradoException
    }
}