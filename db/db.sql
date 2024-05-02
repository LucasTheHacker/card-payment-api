CREATE TABLE Pagamentos (
    NumeroPagamento SERIAL PRIMARY KEY,

    NumeroCartao CHAR(20),

    TipoPessoa INT CHECK (TipoPessoa IN (1, 2)),

    CPFCNPJCliente VARCHAR(14),

    MesVencimentoCartao INT CHECK (MesVencimentoCartao >= 1 AND MesVencimentoCartao <= 12),

    AnoVencimentoCartao INT CHECK (AnoVencimentoCartao >= 2024),

    CVV VARCHAR(4)
);
