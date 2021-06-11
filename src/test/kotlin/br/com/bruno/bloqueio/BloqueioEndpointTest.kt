package br.com.bruno.bloqueio

import br.com.bruno.*
import br.com.bruno.cartao.Cartao
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.cartao.StatusCartao
import br.com.bruno.endereco.Endereco
import br.com.bruno.externo.analise.Analise
import br.com.bruno.externo.analise.ResultadoAnaliseResponse
import br.com.bruno.externo.analise.ResultadoSolicitacao
import br.com.bruno.externo.analise.SolicitacaoAnaliseRequest
import br.com.bruno.externo.cartao.BloqueioCartaoResponse
import br.com.bruno.externo.cartao.Cartoes
import br.com.bruno.proposta.Proposta
import br.com.bruno.proposta.PropostaRepository
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
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class BloqueioEndpointTest(
    val repository: BloqueioRepository,
    val cartaoRepository: CartaoRepository,
    val grpcClient: BloqueioServiceGrpc.BloqueioServiceBlockingStub
){
    @Factory
    class Clients  {
        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): BloqueioServiceGrpc.BloqueioServiceBlockingStub? {
            return BloqueioServiceGrpc.newBlockingStub(channel)
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
            br.com.bruno.endereco.Endereco("rua","cidade", "estado", "num", "cep")
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
                StatusCartao.OK,)
        )
    }

    @AfterEach
    fun after() {
        repository.deleteAll()
    }

    @Test
    internal fun `bloqueia um cartao`(){
        val idCartao = "5366-1437-1861-0020"
        val cartao = cartaoRepository.findByNumeroCartao(idCartao)
        Mockito.`when`(cartoes.bloqueioCartao(idCartao,
            NovoBloqueio( sistemaResponsavel = "proposta",
                idCartao = idCartao)
        ))
            .thenReturn(
                HttpResponse.ok(
                    BloqueioCartaoResponse( resultado = "BLOQUEADO")
                ))
        val response = grpcClient.bloquear(
            BloqueioRequest.newBuilder()
                .setSistemaResponsavel("proposta")
                .setIdCartao(idCartao)
                .build())
        with(response){
            assertNotNull(idCartao)
        }
    }
}