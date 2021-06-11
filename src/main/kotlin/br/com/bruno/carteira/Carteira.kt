package br.com.bruno.carteira

import br.com.bruno.IdentificadorCarteira
import br.com.bruno.cartao.Cartao
import javax.persistence.*

@Entity
class Carteira(
    val email: String,
    @ManyToOne val cartao: Cartao,
    @Enumerated(EnumType.STRING) val identificadorCarteira: IdentificadorCarteira,
    val associacaoId: String
) {

    @Id
    @GeneratedValue
    val id: Long? = null
}