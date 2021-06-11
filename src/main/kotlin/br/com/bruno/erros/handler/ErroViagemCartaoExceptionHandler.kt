package br.com.bruno.erros.handler

import br.com.bruno.erros.exception.CartaoBloqueadoException
import br.com.bruno.erros.exception.CartaoNaoEncontradoException
import br.com.bruno.erros.exception.DocumentoJaCadastradoException
import br.com.bruno.erros.exception.ErroViagemCartaoException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class ErroViagemCartaoExceptionHandler : ExceptionHandler<ErroViagemCartaoException>{

    override fun handle(e: ErroViagemCartaoException): ExceptionHandler.StatusWithDetails {
        return ExceptionHandler.StatusWithDetails(
            Status.UNAVAILABLE
                .withDescription(e.message)
                .withCause(e))
    }

    override fun supports(e: Exception): Boolean {
        return e is ErroViagemCartaoException
    }
}