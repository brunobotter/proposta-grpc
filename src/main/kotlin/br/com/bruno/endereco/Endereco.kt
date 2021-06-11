package br.com.bruno.endereco

import io.micronaut.validation.Validated
import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Embeddable
class Endereco(@field:NotNull @field:NotBlank val cidade: String?,
               @field:NotNull @field:NotBlank val rua: String?,
               @field:NotNull @field:NotBlank val complemento: String?,
               @field:NotNull @field:NotBlank val estado: String?,
               @field:NotNull @field:NotBlank  val cep: String? ) {

}