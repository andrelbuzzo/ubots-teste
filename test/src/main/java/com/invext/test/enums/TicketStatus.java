package com.invext.test.enums;

public enum TicketStatus {
	CREATED("Criado"),
	IN_PROGRESS("Em atendimento"),
	QUEUE("Em fila para ser atendido"),
	FINISHED("Finalizado");

	private String descricao;

	TicketStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
