package br.com.bruno.biometria

import br.com.bruno.BiometriaRequest
import br.com.bruno.BiometriaResponse
import br.com.bruno.BiometriaServiceGrpc
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.erros.exception.CartaoNaoEncontradoException
import br.com.bruno.validacao.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ErrorHandler
class BiometriaEndpoint(
    @Inject val cartaoRepository: CartaoRepository,
    @Inject val biometriaRepository: BiometriaRepository
): BiometriaServiceGrpc.BiometriaServiceImplBase() {

    override fun salvar(request: BiometriaRequest?, responseObserver: StreamObserver<BiometriaResponse>?) {
        val cartao = cartaoRepository.findByNumeroCartao(request?.idCartao)
        if(cartao.isEmpty){
            throw CartaoNaoEncontradoException("Cartao nao encontrado");
        }
        val response = request?.toModel(cartao)
        biometriaRepository.save(response)
        responseObserver?.onNext(BiometriaResponse.newBuilder()
            .setId(response?.id.toString())
            .build())
        responseObserver?.onCompleted()
    }
}