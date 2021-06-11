package br.com.bruno.biometria

import br.com.bruno.BiometriaRequest
import br.com.bruno.cartao.Cartao
import java.time.LocalDateTime
import java.util.*

fun BiometriaRequest.toModel(cartao: Optional<Cartao>): Biometria{
    val biometria64 = Base64.getEncoder().encode(fingerprint.encodeToByteArray())
    return Biometria(cartao = cartao.get(),biometria64,LocalDateTime.now())
}