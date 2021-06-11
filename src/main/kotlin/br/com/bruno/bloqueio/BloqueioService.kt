package br.com.bruno.bloqueio

import br.com.bruno.cartao.Cartao
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.cartao.StatusCartao
import br.com.bruno.erros.exception.CartaoBloqueadoException
import br.com.bruno.erros.exception.CartaoNaoEncontradoException
import br.com.bruno.erros.exception.ErroBloquearCartaoException
import br.com.bruno.externo.cartao.Cartoes
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Validated
@Singleton
class BloqueioService(
    @Inject val cartoes: Cartoes,
    @Inject val cartaoRepository: CartaoRepository
) {

    fun bloquear(cartao: Optional<Cartao>, request: NovoBloqueio?): Bloqueio? {
        if(cartao.isEmpty){
            throw CartaoNaoEncontradoException("Cartao nao encontrado");
        }
        if(cartao.get().statusCartao == StatusCartao.BLOQUEADO){
            throw CartaoBloqueadoException("Nao e possivel bloquear um cartao ja bloqueado");
        }
        val bloqueio = request?.toModel(cartao.get())
        cartao.get().mudaStatusParaBloqueado(bloqueio)
        cartaoRepository.update(cartao.get())
        try{
            cartoes.bloqueioCartao(bloqueio!!.cartao.numeroCartao.toString(), request)
        }catch (e: HttpClientResponseException){
            throw ErroBloquearCartaoException("Falha ao bloquear cartao")
        }
        return bloqueio
    }
}