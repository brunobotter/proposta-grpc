package br.com.bruno.carteira

import br.com.bruno.erros.exception.IdentificadorIncorretoException

enum class IdentificadorCarteira {

    PAYPAL,
    SAMSUNG_PLAY;

    open fun fromString(value: String?): IdentificadorCarteira? {
        if (value == null) throw IdentificadorIncorretoException("Valor incorreto")
        for (IdentificadorCarteira in IdentificadorCarteira.values()) {
            if (value.equals(IdentificadorCarteira.toString(), ignoreCase = true)) {
                return IdentificadorCarteira
            }
        }
        throw IdentificadorIncorretoException("Valor incorreto")
    }
}