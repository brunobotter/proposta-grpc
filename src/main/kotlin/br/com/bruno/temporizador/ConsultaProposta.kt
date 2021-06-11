package br.com.bruno.temporizador

import br.com.bruno.externo.cartao.Cartoes
import br.com.bruno.proposta.PropostaRepository
import br.com.bruno.proposta.Restricao
import io.micronaut.scheduling.annotation.Scheduled
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConsultaProposta(
    @Inject val cartao: Cartoes,
    @Inject val propostaRepository: PropostaRepository
) {

    @Scheduled(fixedDelay = "10s")
    fun consulta(){
        val list = propostaRepository.findBySolicitacaoAndCartaoIsNull(Restricao.ELEGIVEL, null)
        list.forEach{
            val request = cartao.cartaoParaProposta(it.id.toString())
            it.adicionaNovoCartao(request.body().toModel(it))
            propostaRepository.update(it)
        }

    }
}