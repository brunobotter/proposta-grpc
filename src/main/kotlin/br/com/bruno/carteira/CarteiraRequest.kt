package br.com.bruno.carteira

import br.com.bruno.CarteiraRequest
import br.com.bruno.IdentificadorCarteira
import br.com.bruno.cartao.Cartao
import br.com.bruno.externo.cartao.CarteiraResponse
import java.util.*


fun CarteiraRequest.toModel() = NovaCarteira(idCartao, email, carteira)


data class NovaCarteira(
    val idCartao: String,
    val email: String,
    val identificadorCarteira: IdentificadorCarteira
){
    fun toModel(cartao: Cartao, body: Optional<CarteiraResponse>?)= Carteira(email, cartao,identificadorCarteira,body!!.get().id)


}