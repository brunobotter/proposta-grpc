package br.com.bruno.viagem

import br.com.bruno.cartao.Cartao
import com.google.protobuf.Timestamp
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Viagem(
    val destinoViagem: String,
    val instanteAvisoViagem: LocalDate,
    val terminoViagem: LocalDate
) {

    @Id
    @GeneratedValue
    val id: Long? = null

    val ipCliente: String? = null
    val userAgent: String? = null
    @ManyToOne
    val cartao: Cartao? = null
}