package br.com.bruno.viagem

import br.com.bruno.AvisoRequest
import br.com.bruno.BloqueioRequest
import br.com.bruno.BloqueioServiceGrpc
import br.com.bruno.ViagemServiceGrpc
import br.com.bruno.bloqueio.BloqueioRepository
import br.com.bruno.bloqueio.NovoBloqueio
import br.com.bruno.cartao.Cartao
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.cartao.StatusCartao
import br.com.bruno.endereco.Endereco
import br.com.bruno.externo.cartao.AvisoViagemResponse
import br.com.bruno.externo.cartao.BloqueioCartaoResponse
import br.com.bruno.externo.cartao.Cartoes
import br.com.bruno.proposta.Proposta
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class ViagemEndpointTest(
    val cartaoRepository: CartaoRepository,
    val grpcClient: ViagemServiceGrpc.ViagemServiceBlockingStub
) {
    @Factory
    class Clients {
        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): ViagemServiceGrpc.ViagemServiceBlockingStub? {
            return ViagemServiceGrpc.newBlockingStub(channel)
        }
    }

    @Inject
    lateinit var cartoes: Cartoes

    companion object {
        val proposta = Proposta(
            "420.615.250-08",
            "bruno@hotmail.com",
            "bruno",
            2000.0,
            Endereco("rua", "cidade", "estado", "num", "cep")
        )
    }

    @MockBean(Cartoes::class)
    fun analise(): Cartoes? {
        return Mockito.mock(Cartoes::class.java)
    }

    @BeforeEach
    fun setup() {
        cartaoRepository.save(
            Cartao(
                "5366-1437-1861-0020",
                proposta,
                StatusCartao.OK,
            )
        )
    }

    @AfterEach
    fun after() {
        cartaoRepository.deleteAll()
    }
    @Test
    internal fun `adicionar status de viagem ao cartao`(){
        val idCartao = "5366-1437-1861-0020"
        val cartao = cartaoRepository.findByNumeroCartao(idCartao)
        Mockito.`when`(cartoes.avisoViagem(idCartao,
            NovaViagem( destinoViagem = "rj",
                instanteAvisoViagem = LocalDate.now(),
                terminoViagem = LocalDate.parse("2021-10-10"))
        ))
            .thenReturn(
                HttpResponse.ok(
                    AvisoViagemResponse( resultado = "VIAJANDO")
                ))
        val response = grpcClient.avisoViagem(
            AvisoRequest.newBuilder()
                .setDestino("RJ")
                .setIdCartao(idCartao)
                .setValidoAte("2021-10-10")
                .build())
        with(response){
            assertNotNull(idCartao)
        }
    }

}