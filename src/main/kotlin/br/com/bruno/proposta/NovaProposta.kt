package br.com.bruno.proposta

import br.com.bruno.endereco.EnderecoRequest
import br.com.bruno.validacao.CpfOuCnpj
import io.micronaut.core.annotation.Introspected
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Introspected
data class NovaProposta(@field:Email val email: String?,
                        @field:NotNull @field:NotBlank val nome: String?,
                        @field:Positive val salario: Double?,
                        @field:CpfOuCnpj val documento: String?,
                        @Valid val endereco: EnderecoRequest?) {


    fun toModel() = Proposta(
        email = email,
        nome = nome,
        salario = salario,
        documento = documento,
        endereco = endereco?.toModel()
    )

}