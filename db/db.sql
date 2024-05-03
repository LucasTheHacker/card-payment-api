CREATE TABLE Pagamentos (
    NumeroPagamento SERIAL PRIMARY KEY,

    NumeroCartao VARCHAR(50),

    TipoPessoa INT CHECK (TipoPessoa IN (1, 2)),

    CPFCNPJCliente VARCHAR(50),

    MesVencimentoCartao INT CHECK (MesVencimentoCartao >= 1 AND MesVencimentoCartao <= 12),

    AnoVencimentoCartao INT CHECK (AnoVencimentoCartao >= 2024),

    CVV VARCHAR(50)
);
