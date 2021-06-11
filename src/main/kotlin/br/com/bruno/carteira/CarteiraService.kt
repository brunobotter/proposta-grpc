package br.com.bruno.carteira

import br.com.bruno.cartao.Cartao
import br.com.bruno.erros.exception.CartaoNaoEncontradoException
import br.com.bruno.erros.exception.ErroCarteiraException
import br.com.bruno.erros.exception.IdentificadorJaExisteException
import br.com.bruno.externo.cartao.Cartoes
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Validated
@Singleton
class CarteiraService(
    @Inject val cartoes: Cartoes,
    @Inject val carteiraRepository: CarteiraRepository
) {

    fun adicionar(cartao: Optional<Cartao>, request: NovaCarteira?): Carteira? {
        if (cartao.isEmpty) {
            throw CartaoNaoEncontradoException("Cartao nao encontrado");
        }
        val possivelCarteira = carteiraRepository.findByCartaoAndIdentificadorCarteira(cartao.get(), request?.identificadorCarteira)
        if (possivelCarteira!!.isPresent) {
            throw IdentificadorJaExisteException("Ja existe uma carteira associada a este cartao")
        }
        try{
            val response = cartoes.carteira(cartao.get().numeroCartao, request)
            return carteiraRepository.save(request!!.toModel(cartao.get(), response?.body))
        }catch (e: HttpClientResponseException){
            throw ErroCarteiraException("Falha na inserção da carteira")
        }
    }


}