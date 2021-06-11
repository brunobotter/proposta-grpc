package br.com.bruno.erros.handler

import br.com.bruno.erros.exception.CartaoNaoEncontradoException
import br.com.bruno.erros.exception.DocumentoJaCadastradoException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class CartaoNaoEncontradoExceptionHandler : ExceptionHandler<CartaoNaoEncontradoException>{

    override fun handle(e: CartaoNaoEncontradoException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.NOT_FOUND
                .withDescription(e.message)
                .withCause(e))
    }

    override fun supports(e: Exception): Boolean {
        return e is CartaoNaoEncontradoException
    }
}