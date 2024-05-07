CREATE DATABASE cartoes;

\c cartoes;

CREATE TABLE Pagamentos (
    NumeroPagamento SERIAL PRIMARY KEY,

    NumeroCartao VARCHAR(30),

    TipoPessoa INT CHECK (TipoPessoa IN (1, 2)),

    CPFCNPJCliente VARCHAR(30),

    MesVencimentoCartao INT CHECK (MesVencimentoCartao >= 1 AND MesVencimentoCartao <= 12),

    AnoVencimentoCartao INT CHECK (AnoVencimentoCartao >= 2024),

    CVV VARCHAR(10),

	ValorPagamento VARCHAR(20),

	DataPagamento VARCHAR(15)
);
