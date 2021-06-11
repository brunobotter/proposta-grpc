package br.com.bruno.viagem

import br.com.bruno.AvisoRequest
import br.com.bruno.AvisoResponse
import br.com.bruno.ViagemServiceGrpc
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.validacao.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class ViagemEndpoint(
    @Inject val viagemService: ViagemService,
    @Inject val cartaoRepository: CartaoRepository
): ViagemServiceGrpc.ViagemServiceImplBase() {

    override fun avisoViagem(request: AvisoRequest?, responseObserver: StreamObserver<AvisoResponse>?) {
        val cartao = cartaoRepository.findByNumeroCartao(request?.idCartao)
        val viagem = request?.toModel()
        val service = viagemService.avisoViagem(viagem, cartao)
        responseObserver?.onNext(AvisoResponse.newBuilder()
            .setResultado(service.statusCartao.name)
            .build())
        responseObserver?.onCompleted()
    }
}