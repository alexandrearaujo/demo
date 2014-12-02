package br.com.mv.dispensacaomedicamento.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class Cidadao {

	@Id
	@Column(name="CD_CIDADAO")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
