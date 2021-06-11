package br.com.bruno.erros.handler

import br.com.bruno.erros.exception.CartaoBloqueadoException
import br.com.bruno.erros.exception.CartaoNaoEncontradoException
import br.com.bruno.erros.exception.DocumentoJaCadastradoException
import br.com.bruno.erros.exception.ErroBloquearCartaoException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class ErroBloquearCartaoExceptionHandler : ExceptionHandler<ErroBloquearCartaoException>{

    override fun handle(e: ErroBloquearCartaoException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.ABORTED
                .withDescription(e.message)
                .withCause(e))
    }

    override fun supports(e: Exception): Boolean {
        return e is ErroBloquearCartaoException
    }
}