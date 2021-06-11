package br.com.bruno.carteira

import br.com.bruno.CarteiraRequest
import br.com.bruno.CarteiraResponse
import br.com.bruno.CarteiraServiceGrpc
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.validacao.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class CarteiraEndpoint(
    @Inject val cartaoRepository: CartaoRepository,
    @Inject val carteiraService: CarteiraService
): CarteiraServiceGrpc.CarteiraServiceImplBase() {

    override fun adicionar(request: CarteiraRequest?, responseObserver: StreamObserver<CarteiraResponse>?) {
        val cartao = cartaoRepository.findByNumeroCartao(request?.idCartao)
        val carteira = request?.toModel()
        val service = carteiraService.adicionar(cartao, carteira)
        responseObserver?.onNext(CarteiraResponse.newBuilder()
            .setId(service!!.id.toString())
            .setResultado(service!!.identificadorCarteira.name)
            .build())
        responseObserver?.onCompleted()
    }
}