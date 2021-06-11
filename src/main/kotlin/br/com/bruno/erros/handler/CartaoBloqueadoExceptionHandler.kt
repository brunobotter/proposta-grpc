package br.com.bruno.erros.handler

import br.com.bruno.erros.exception.CartaoBloqueadoException
import br.com.bruno.erros.exception.CartaoNaoEncontradoException
import br.com.bruno.erros.exception.DocumentoJaCadastradoException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class CartaoBloqueadoExceptionHandler : ExceptionHandler<CartaoBloqueadoException>{

    override fun handle(e: CartaoBloqueadoException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.UNAVAILABLE
                .withDescription(e.message)
                .withCause(e))
    }

    override fun supports(e: Exception): Boolean {
        return e is CartaoBloqueadoException
    }
}