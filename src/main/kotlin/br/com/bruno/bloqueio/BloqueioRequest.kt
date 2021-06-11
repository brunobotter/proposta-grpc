package br.com.bruno.bloqueio

import br.com.bruno.BloqueioRequest
import br.com.bruno.cartao.Cartao
import java.time.LocalDate


fun BloqueioRequest.toModel()= NovoBloqueio(sistemaResponsavel, idCartao)


data class NovoBloqueio(
    val sistemaResponsavel: String,
    val idCartao: String
){
    fun toModel(cartao: Cartao) = Bloqueio(cartao = cartao,dataBloqueio = LocalDate.now())
}

