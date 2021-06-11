package br.com.bruno.viagem

import br.com.bruno.AvisoRequest
import com.google.protobuf.Timestamp
import java.time.LocalDate

fun AvisoRequest.toModel()=NovaViagem(destino, LocalDate.now(),LocalDate.parse(validoAte))


data class NovaViagem(
    val destinoViagem: String,
    val instanteAvisoViagem: LocalDate,
    val terminoViagem: LocalDate
){
    fun toModel()=Viagem(destinoViagem,instanteAvisoViagem,terminoViagem)


}