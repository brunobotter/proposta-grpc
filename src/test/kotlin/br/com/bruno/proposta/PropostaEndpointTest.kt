package br.com.bruno.proposta

import br.com.bruno.*
import br.com.bruno.Endereco.*
import br.com.bruno.cartao.Cartao
import br.com.bruno.cartao.CartaoRepository
import br.com.bruno.cartao.StatusCartao
import br.com.bruno.endereco.Endereco
import br.com.bruno.endereco.EnderecoRequest
import br.com.bruno.externo.analise.Analise
import br.com.bruno.externo.analise.ResultadoAnaliseResponse
import br.com.bruno.externo.analise.ResultadoSolicitacao
import br.com.bruno.externo.analise.SolicitacaoAnaliseRequest
import br.com.bruno.externo.cartao.AvisoViagemResponse
import br.com.bruno.externo.cartao.Cartoes
import br.com.bruno.viagem.NovaViagem
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest()
internal class PropostaEndpointTest(
    val propostaRepository: PropostaRepository,
    val grpcClient: PropostaServiceGrpc.PropostaServiceBlockingStub
) {
    @Factory
    class Clients {
        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): PropostaServiceGrpc.PropostaServiceBlockingStub? {
            return PropostaServiceGrpc.newBlockingStub(channel)
        }
    }

    companion object{
        val endereco = Endereco("fsafsa","fafasfas","fafsafsa","fsafsafsa","dfdfds")
    }

    @Inject
    lateinit var analise: Analise

    @MockBean(Analise::class)
    fun analise(): Analise? {
        return Mockito.mock(Analise::class.java)
    }

    @AfterEach
    fun after() {
        propostaRepository.deleteAll()
    }

    private fun proposta() = Proposta(
        "066.517.830-17",
        "fafasfsaf@gmail.com",
        "fsafsafs",
        2000.0,
        endereco
    )

    private fun solicitacaoAnaliseRequest() = SolicitacaoAnaliseRequest("066.517.830-17",
        "fsafsafs",
        "100" )
    private fun resultadoAnaliseResponse() = ResultadoAnaliseResponse(
        "066.517.830-17",
        "fsafsafs",
        ResultadoSolicitacao.SEM_RESTRICAO,
        "100")

    @Test
    internal fun `adicionar proposta`() {

        `when`(analise.analisaRestricao(solicitacaoAnaliseRequest()))
            .thenReturn(HttpResponse.ok(resultadoAnaliseResponse()))

        val response = grpcClient.salvar(
            PropostaRequest.newBuilder()
                .setSalario(2000.0)
                .setDocumento("066.517.830-17")
                .setEmail("fafasfsaf@gmail.com")
                .setNome("fsafsafs")
                .setEndereco(
                    newBuilder()
                        .setCep("dsfas")
                        .setCidade("fsafsaa")
                        .setComplemento("fsafas")
                        .setRua("fsdafasd")
                        .setEstado("fdfdsfds")
                        .build())
                .build()
        )
        with(response) {
            assertNotNull(id)
        }
    }

}