package br.com.bruno.erros.handler

import br.com.bruno.erros.exception.DocumentoJaCadastradoException
import br.com.bruno.erros.exception.IdentificadorJaExisteException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class IdentificadorJaExisteExceptionHandler : ExceptionHandler<IdentificadorJaExisteException>{

    override fun handle(e: IdentificadorJaExisteException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.ALREADY_EXISTS
                .withDescription(e.message)
                .withCause(e))
    }

    override fun supports(e: Exception): Boolean {
        return e is IdentificadorJaExisteException
    }
}