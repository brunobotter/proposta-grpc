package br.com.bruno.erros.handler

import br.com.bruno.erros.exception.*
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class ErroCarteiraExceptionHandler : ExceptionHandler<ErroCarteiraException>{

    override fun handle(e: ErroCarteiraException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.UNAVAILABLE
                .withDescription(e.message)
                .withCause(e))
    }

    override fun supports(e: Exception): Boolean {
        return e is ErroCarteiraException
    }
}