package br.com.bruno.cartao

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface CartaoRepository: JpaRepository<Cartao, Long> {
    fun findByNumeroCartao(idCartao: String?): Optional<Cartao>
}