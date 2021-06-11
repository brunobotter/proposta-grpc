package br.com.bruno.carteira

import br.com.bruno.*
import br.com.bruno.IdentificadorCarteira
import br.com.bruno.cartao.Cartao
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.cartao.StatusCartao
import br.com.bruno.endereco.Endereco
import br.com.bruno.externo.cartao.CarteiraResponse
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
import org.mockito.Mockito.`when`
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class CarteiraEndpointTest(
    val cartaoRepository: CartaoRepository,
    val carteiraRepository: CarteiraRepository,
    val grpcClient: CarteiraServiceGrpc.CarteiraServiceBlockingStub
) {
    @Factory
    class Clients {
        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): CarteiraServiceGrpc.CarteiraServiceBlockingStub? {
            return CarteiraServiceGrpc.newBlockingStub(channel)
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
                "5249-6142-8426-1698",
                proposta,
                StatusCartao.OK,
            )
        )
    }

    @AfterEach
    fun after() {
        cartaoRepository.deleteAll()
        carteiraRepository.deleteAll()
    }

    @Test
    internal fun `adicionar status de carteira ao cartao`(){
        val idCartao = "5249-6142-8426-1698"
        val cartao = cartaoRepository.findByNumeroCartao(idCartao)
        assertTrue(cartao.isPresent)
        val possivelCarteira = carteiraRepository.findByCartaoAndIdentificadorCarteira(cartao.get(), IdentificadorCarteira.PAYPAL)
        assertTrue(possivelCarteira!!.isEmpty)

        `when`(cartoes.carteira(idCartao,
            NovaCarteira(idCartao,"bruno@hotmail.com",IdentificadorCarteira.PAYPAL)
        )).thenReturn( HttpResponse.ok(
            CarteiraResponse( resultado = "PAYPAL",id = "200" )
        ))
        carteiraRepository.save(Carteira("bruno@hotmail.com", cartao.get(),IdentificadorCarteira.PAYPAL,"200"))
        val response = grpcClient.adicionar(
            CarteiraRequest.newBuilder()
                .setCarteira(IdentificadorCarteira.PAYPAL)
                .setIdCartao(idCartao)
                .setEmail("2021-10-10")
                .build())
        with(response){
            assertNotNull(idCartao)
        }
    }

}