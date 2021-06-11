package br.com.bruno.carteira

import br.com.bruno.IdentificadorCarteira
import br.com.bruno.cartao.Cartao
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface CarteiraRepository: JpaRepository<Carteira, Long> {

    fun findByCartaoAndIdentificadorCarteira(
        cartao: Cartao?,
        identificadorCarteira: IdentificadorCarteira?
    ): Optional<Carteira?>?

}