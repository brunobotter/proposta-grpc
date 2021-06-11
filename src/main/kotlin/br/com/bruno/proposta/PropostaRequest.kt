package br.com.bruno.proposta

import br.com.bruno.Endereco
import br.com.bruno.PropostaRequest
import br.com.bruno.endereco.EnderecoRequest

fun PropostaRequest.toModel() = NovaProposta(email = email, nome = nome, salario = salario, documento = documento, endereco = endereco.toModel() )


private fun Endereco.toModel() = EnderecoRequest(cidade = cidade, rua = rua, complemento = complemento, estado = estado, cep = cep)
