package br.com.bruno.externo.cartao

import br.com.bruno.CarteiraRequest
import br.com.bruno.bloqueio.NovoBloqueio
import br.com.bruno.cartao.Cartao
import br.com.bruno.cartao.StatusCartao
import br.com.bruno.carteira.NovaCarteira
import br.com.bruno.proposta.Proposta
import br.com.bruno.viagem.NovaViagem
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${cartao.host.url}")
interface Cartoes {

    @Get
    fun cartaoParaProposta(@QueryValue idProposta: String): HttpResponse<CartaoClientResponse>


    @Post("/{id}/bloqueios",  produces = [MediaType.APPLICATION_JSON],
        consumes = [MediaType.APPLICATION_JSON])
    fun bloqueioCartao(@PathVariable(name = "id") id: String?,@Body request: NovoBloqueio?): HttpResponse<BloqueioCartaoResponse>


    @Post("/{id}/avisos",  produces = [MediaType.APPLICATION_JSON],
        consumes = [MediaType.APPLICATION_JSON])
    fun avisoViagem(@PathVariable(name = "id") id: String?, @Body request: NovaViagem?): HttpResponse<AvisoViagemResponse>?

    @Post("/{id}/carteiras",  produces = [MediaType.APPLICATION_JSON],
        consumes = [MediaType.APPLICATION_JSON])
    fun carteira(@PathVariable(name = "id") id: String?, @Body request: NovaCarteira?): HttpResponse<CarteiraResponse>?

}

data class CarteiraResponse(
    val resultado: String,
    val id: String
){}

data class AvisoViagemResponse(val resultado: String){

}

data class BloqueioCartaoResponse(val resultado: String){

}
data class CartaoClientResponse(val id: String,
                                val idProposta: String,
                                val titular: String){
    override fun toString(): String {
        return "CartaoClientResponse(id='$id', idProposta='$idProposta', titular='$titular')"
    }

    fun toModel(proposta: Proposta): Cartao {
        return Cartao(id,proposta, StatusCartao.OK )
    }
}