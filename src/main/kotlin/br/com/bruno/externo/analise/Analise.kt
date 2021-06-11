package br.com.bruno.externo.analise

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${analise.host.url}")
interface Analise {

    @Post(
        produces = [MediaType.APPLICATION_JSON],
        consumes = [MediaType.APPLICATION_JSON])
    fun analisaRestricao(@Body request: SolicitacaoAnaliseRequest): HttpResponse<ResultadoAnaliseResponse>
}

data class SolicitacaoAnaliseRequest(val documento: String?,
                                     val nome: String?,
                                     val idProposta: String?){
}

data class ResultadoAnaliseResponse(
    val documento: String,
    val nome: String,
    val resultadoSolicitacao: ResultadoSolicitacao,
    val idProposta: String
){

}

enum class ResultadoSolicitacao{
    COM_RESTRICAO, SEM_RESTRICAO
}