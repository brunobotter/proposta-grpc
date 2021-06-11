package br.com.bruno.proposta

import br.com.bruno.*
import br.com.bruno.Endereco
import br.com.bruno.validacao.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton
@ErrorHandler
@Singleton
class PropostaEndpoint(@Inject val propostaService: PropostaService): PropostaServiceGrpc.PropostaServiceImplBase() {

    override fun salvar(
        request: PropostaRequest?,
        responseObserver: StreamObserver<PropostaResponse>?
    ) {
        val proposta = request?.toModel()
        val service = propostaService.salvar(proposta)
        responseObserver?.onNext(PropostaResponse.newBuilder()
            .setId(service!!.id.toString())
            .build())
        responseObserver?.onCompleted()
    }

    override fun consultar(
        request: PropostaConsultaRequest?,
        responseObserver: StreamObserver<PropostaConsultaResponse>?
    ) {
        val service = propostaService.consultar(request)
        responseObserver?.onNext(PropostaConsultaResponse.newBuilder()
            .setDocumento(service.documento)
            .setIdProposta(service.id.toString())
            .setNome(service.nome)
            .setSalario(service.salario!!)
            .setStatus(service.solicitacao?.name)
            .setEndereco(Endereco.newBuilder()
                .setCep(service.endereco!!.cep)
                .setCidade(service.endereco.cidade)
                .setComplemento(service.endereco.complemento)
                .setEstado(service.endereco.estado)
                .setRua(service.endereco.rua)
                .build())
            .build())
        responseObserver?.onCompleted()
    }
}