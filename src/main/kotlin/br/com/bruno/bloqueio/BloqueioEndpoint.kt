package br.com.bruno.bloqueio

import br.com.bruno.BloqueioRequest
import br.com.bruno.BloqueioResponse
import br.com.bruno.BloqueioServiceGrpc
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.validacao.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class BloqueioEndpoint(
    @Inject val cartaoRepository: CartaoRepository,
    @Inject val bloqueioService: BloqueioService
): BloqueioServiceGrpc.BloqueioServiceImplBase() {

    override fun bloquear(request: BloqueioRequest?, responseObserver: StreamObserver<BloqueioResponse>?) {
        val cartao = cartaoRepository.findByNumeroCartao(request?.idCartao)
        val bloqueio = request?.toModel()
        val service = bloqueioService.bloquear(cartao, bloqueio)


        responseObserver?.onNext(BloqueioResponse.newBuilder()
            .setStatus(service!!.cartao.statusCartao.name)
            .build())
        responseObserver?.onCompleted()
    }
}