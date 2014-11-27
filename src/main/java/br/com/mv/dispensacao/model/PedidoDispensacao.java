package br.com.mv.dispensacao.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PR_PEDIDO_DISPEN")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor public class PedidoDispensacao {

	@Id
	@SequenceGenerator(name = "SEQ_PEDIDO_DISPENSACAO", sequenceName = "SEQ_PEDIDO_DISPENSACAO", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDO_DISPENSACAO")
	@Column(name = "CD_PEDIDO")
	private Long id;
	
	@NotNull
	@Size(max=5)
	@Column(name = "REGISTRO_HC")
	private Integer registroHC;
	
	@OneToMany(mappedBy="pedidoDispensacao", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ItemPedidoDispensacao> itens;
}
