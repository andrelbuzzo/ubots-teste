package com.invext.test.enums;

public enum TicketType {
	CARTAO("Problemas com cartão"),
	EMPRESTIMO("Contratação de empréstimo"),
	OUTROS("Outros assuntos");

	private String descricao;

	TicketType(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
