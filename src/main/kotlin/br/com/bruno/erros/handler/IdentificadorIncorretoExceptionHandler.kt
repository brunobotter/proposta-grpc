package br.com.bruno.erros.handler

import br.com.bruno.erros.exception.CartaoBloqueadoException
import br.com.bruno.erros.exception.CartaoNaoEncontradoException
import br.com.bruno.erros.exception.DocumentoJaCadastradoException
import br.com.bruno.erros.exception.IdentificadorIncorretoException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class IdentificadorIncorretoExceptionHandler : ExceptionHandler<IdentificadorIncorretoException>{

    override fun handle(e: IdentificadorIncorretoException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.INVALID_ARGUMENT
                .withDescription(e.message)
                .withCause(e))
    }

    override fun supports(e: Exception): Boolean {
        return e is IdentificadorIncorretoException
    }
}