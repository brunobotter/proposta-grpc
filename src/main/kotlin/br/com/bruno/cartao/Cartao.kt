package br.com.bruno.cartao

import br.com.bruno.biometria.Biometria
import br.com.bruno.bloqueio.Bloqueio
import br.com.bruno.proposta.Proposta
import br.com.bruno.viagem.NovaViagem
import br.com.bruno.viagem.Viagem
import javax.persistence.*

@Entity
class Cartao(
    val numeroCartao: String,
    @OneToOne(mappedBy = "cartao", cascade = [CascadeType.MERGE]) val proposta: Proposta,
    @Enumerated(EnumType.STRING) var statusCartao: StatusCartao
) {

    @Id
    @GeneratedValue
    val id: Long? = null
    @OneToMany(mappedBy = "cartao",cascade = [CascadeType.MERGE],fetch = FetchType.EAGER) val viagem: MutableList<Viagem>? = null
    @OneToMany(mappedBy = "cartao") val biometrias: MutableList<Biometria>? = null
    @OneToOne(mappedBy = "cartao", cascade = [CascadeType.MERGE]) var bloqueio: Bloqueio? = null

    fun mudaStatusParaBloqueado(bloqueio: Bloqueio?){
        this.statusCartao = StatusCartao.BLOQUEADO
        this.bloqueio = bloqueio
    }

    fun avisoViagem(viagem: Viagem?) {
        this.statusCartao = StatusCartao.VIAJANDO
        this.viagem?.add(viagem!!)
    }
}