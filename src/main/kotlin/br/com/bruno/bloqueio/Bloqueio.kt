package br.com.bruno.bloqueio

import br.com.bruno.cartao.Cartao
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class Bloqueio(
    @field:NotNull @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER) var cartao: Cartao,
    val dataBloqueio: LocalDate
) {


    @Id
    @GeneratedValue
    val id: Long? = null

    val ipCliente: String? = null
    val userAgent: String? = null


}