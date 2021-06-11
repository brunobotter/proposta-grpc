package br.com.bruno.proposta

import br.com.bruno.cartao.Cartao
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface PropostaRepository: JpaRepository<Proposta,Long> {
    fun findByDocumento(documento: String?): Optional<Proposta>

    fun findBySolicitacao(solicitacao: Restricao): List<Proposta>

    fun findBySolicitacaoAndCartaoIsNull(solicitacao: Restricao, cartao: Cartao?): List<Proposta>
}