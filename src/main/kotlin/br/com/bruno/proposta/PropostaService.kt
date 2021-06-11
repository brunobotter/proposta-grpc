package br.com.bruno.proposta

import br.com.bruno.PropostaConsultaRequest
import br.com.bruno.erros.exception.DocumentoJaCadastradoException
import br.com.bruno.externo.analise.Analise
import br.com.bruno.externo.analise.ResultadoAnaliseResponse
import br.com.bruno.externo.analise.SolicitacaoAnaliseRequest
import io.micronaut.data.annotation.Repository
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.inject.Singleton

@Validated
@Singleton
class PropostaService(@Inject val repository: PropostaRepository,
                      @Inject val analise: Analise) {

    fun salvar(novaProposta: NovaProposta?): Proposta? {
        if(repository.findByDocumento(novaProposta?.documento).isPresent){
            throw DocumentoJaCadastradoException("CPF ou CNPJ ja cadastrado no sistema")
        }
        val proposta = novaProposta?.toModel()
        repository.save(proposta)
        verificaRestricao(proposta)
        return proposta
    }

    private fun verificaRestricao(proposta: Proposta?) {
        val request = proposta?.toAnaliseCartao()
        val response = analise.analisaRestricao(request!!)
        if(proposta.atualizaRestricao(response.body().resultadoSolicitacao.name)){
            repository.update(proposta)
        }else{
            repository.update(proposta)
        }
    }

    fun consultar(request: PropostaConsultaRequest?): Proposta {
        val response = repository.findById(request?.idProposta?.toLong())
        return response.get()
    }


}