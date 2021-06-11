package br.com.bruno.proposta

import br.com.bruno.externo.analise.SolicitacaoAnaliseRequest
import br.com.bruno.cartao.Cartao
import br.com.bruno.endereco.Endereco
import br.com.bruno.validacao.CpfOuCnpj
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Entity
class Proposta(
    @field:CpfOuCnpj val documento: String?,
    @field:Email val email: String?,
    @field:NotNull @field:NotBlank val nome: String?,
    @field:Positive val salario: Double?,
    @field:Valid @Embedded val endereco: Endereco?
) {

    @Id
    @GeneratedValue
    val id: Long? = null

    @Enumerated(EnumType.STRING)
    var solicitacao: Restricao? = null

    @OneToOne(cascade = [CascadeType.MERGE])
    var cartao: Cartao? = null

    fun atualizaRestricao(response: String):Boolean{
        if(response == "SEM_RESTRICAO"){
            this.solicitacao = Restricao.ELEGIVEL
            return true
        }
        this.solicitacao = Restricao.NAO_ELEGIVEL
        return false
    }
    fun toAnaliseCartao(): SolicitacaoAnaliseRequest {
        return SolicitacaoAnaliseRequest(documento = documento,nome= nome,idProposta = id.toString())
    }

    fun adicionaNovoCartao(cartao: Cartao) {
        this.cartao = cartao
    }


}