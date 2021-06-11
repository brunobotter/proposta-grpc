package br.com.bruno.endereco

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class EnderecoRequest(@field:NotNull @field:NotBlank val cidade: String,
                           @field:NotNull @field:NotBlank val rua: String,
                           @field:NotNull @field:NotBlank val complemento: String,
                           @field:NotNull @field:NotBlank val estado: String,
                           @field:NotNull @field:NotBlank val cep: String) {

    fun toModel()= Endereco(cidade, rua, complemento, estado, cep)

}