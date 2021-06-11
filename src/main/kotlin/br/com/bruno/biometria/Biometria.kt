package br.com.bruno.biometria

import br.com.bruno.cartao.Cartao
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.FutureOrPresent
import javax.validation.constraints.NotNull

@Entity
class Biometria(
    @field:NotNull @ManyToOne val cartao: Cartao,
    @field:NotNull val fingerprint: ByteArray,
    val dataCadastroBiometria: LocalDateTime,

    ) {
    @Id
    @GeneratedValue
    val id: Long? = null
    override fun toString(): String {
        return "Biometria(cartao=$cartao, fingerprint=${fingerprint.contentToString()}, dataCadastroBiometria=$dataCadastroBiometria, id=$id)"
    }


}