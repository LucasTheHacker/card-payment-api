CREATE TABLE IF NOT EXISTS Pagamentos (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    NumeroCartao VARCHAR(50),
    TipoPessoa INT,
    CPFCNPJCliente VARCHAR(50),
    MesVencimentoCartao INT,
    AnoVencimentoCrtao INT,
    CVV VARCHAR(20),
    ValorPagamento VARCHAR(20),
    DataPagamento VARCHAR(255)
);

INSERT INTO Pagamentos (INSERT INTO Pagamentos (NumeroCartao, TipoPessoa, CPFCNPJCliente, MesVencimentoCartao, AnoVencimentoCartao, CVV, ValorPagamento, DataPagamento) VALUES ("1111222233334444", 1, "11122233344", 12, 2024, "1234", "4568.00", "2024-07-08 14:24:48")
INSERT INTO Pagamentos (INSERT INTO Pagamentos (NumeroCartao, TipoPessoa, CPFCNPJCliente, MesVencimentoCartao, AnoVencimentoCartao, CVV, ValorPagamento, DataPagamento) VALUES ("4444333322221111", 2, "11122233344789", 12, 2024, "1234", "726.00", "2023-09-01 05:10:59")
INSERT INTO Pagamentos (INSERT INTO Pagamentos (NumeroCartao, TipoPessoa, CPFCNPJCliente, MesVencimentoCartao, AnoVencimentoCartao, CVV, ValorPagamento, DataPagamento) VALUES ("9999777766663333", 1, "11122233344", 12, 2024, "1234", "6358.00", "2022-07-08 07:10:20")
INSERT INTO Pagamentos (INSERT INTO Pagamentos (NumeroCartao, TipoPessoa, CPFCNPJCliente, MesVencimentoCartao, AnoVencimentoCartao, CVV, ValorPagamento, DataPagamento) VALUES ("1010555544432589", 2, "11122233348549", 12, 2024, "1234", "7412.00", "2020-07-15 10:48:20")
