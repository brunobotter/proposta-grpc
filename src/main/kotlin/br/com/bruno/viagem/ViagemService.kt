package br.com.bruno.viagem

import br.com.bruno.cartao.Cartao
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.cartao.StatusCartao
import br.com.bruno.erros.exception.CartaoBloqueadoException
import br.com.bruno.erros.exception.CartaoNaoEncontradoException
import br.com.bruno.erros.exception.ErroViagemCartaoException
import br.com.bruno.externo.cartao.Cartoes
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Validated
@Singleton
class ViagemService(
    @Inject val cartaoRepository: CartaoRepository,
    @Inject val cartoes: Cartoes
) {

    fun avisoViagem(viagem: NovaViagem?, cartao: Optional<Cartao>): Cartao{
        if(cartao.isEmpty){
            throw CartaoNaoEncontradoException("Cartao nao encontrado");
        }
        if(cartao.get().statusCartao == StatusCartao.BLOQUEADO){
            throw CartaoBloqueadoException("Cartao esta bloqueado");
        }
        if(cartao.get().statusCartao == StatusCartao.VIAJANDO){
            throw CartaoBloqueadoException("Usuario ja encontra-se viajando");
        }
        try{
            val response = cartoes.avisoViagem(cartao.get().numeroCartao,viagem)
            cartao.get().avisoViagem(viagem?.toModel())
            cartaoRepository.update(cartao.get())
        }catch (e: HttpClientResponseException){
            throw ErroViagemCartaoException("Falha no aviso de viagem do cartao")
        }
        return cartao.get()
    }
}