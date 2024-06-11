package io.github.lucasthehacker.apipagamentocartao.domain.enums;

public enum TipoPessoa {
    PF(1),
    PJ(2);

    private final Integer codTipoPessoa;

    TipoPessoa(Integer codTipoPessoa) {this.codTipoPessoa = codTipoPessoa;}

    public Integer get() { return codTipoPessoa; }
}
