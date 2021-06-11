package br.com.bruno.biometria

import br.com.bruno.BiometriaRequest
import br.com.bruno.BiometriaServiceGrpc
import br.com.bruno.BloqueioRequest
import br.com.bruno.BloqueioServiceGrpc
import br.com.bruno.bloqueio.BloqueioRepository
import br.com.bruno.bloqueio.NovoBloqueio
import br.com.bruno.cartao.Cartao
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.cartao.StatusCartao
import br.com.bruno.endereco.Endereco
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
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class BiometriaEndpointTest(val repository: BiometriaRepository,
                                     val cartaoRepository: CartaoRepository,
                                     val grpcClient: BiometriaServiceGrpc.BiometriaServiceBlockingStub){
    @Factory
    class Clients  {
        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): BiometriaServiceGrpc.BiometriaServiceBlockingStub? {
            return BiometriaServiceGrpc.newBlockingStub(channel)
        }
    }


    companion object {
        val proposta = Proposta(
            "420.615.250-08",
            "bruno@hotmail.com",
            "bruno",
            2000.0,
            Endereco("rua","cidade", "estado", "num", "cep")
        )
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
    internal fun `adiciona biometria a um cartao`(){
        val idCartao = "5366-1437-1861-0020"
        val cartao = cartaoRepository.findByNumeroCartao(idCartao)

        val response = grpcClient.salvar(
            BiometriaRequest.newBuilder()
                .setIdCartao(idCartao)
                .setFingerprint("fdsfdsfdsfds")
                .build())
        with(response){
            assertNotNull(response.id)
        }
    }
}