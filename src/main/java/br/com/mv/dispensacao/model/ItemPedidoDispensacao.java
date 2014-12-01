package br.com.mv.dispensacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PR_ITEM_PEDIDO_DISPEN")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor public class ItemPedidoDispensacao {

	@Id
	@SequenceGenerator(name = "SEQ_ITEM_PEDIDO_DISPENSACAO", sequenceName = "SEQ_ITEM_PEDIDO_DISPENSACAO", allocationSize = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM_PEDIDO_DISPENSACAO")
	@Column(name = "CD_ITEM_PEDIDO")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "CD_PEDIDO", referencedColumnName = "CD_PEDIDO", nullable = false)
    private PedidoDispensacao pedidoDispensacao;
	
	@NotNull
	@Size(max=5)
	@Column(name = "NR_QUANTIDADE", nullable = false, length = 5)
	private Integer quantidade;
	
	@Size(max=250)
	@NotNull
	@Column(name = "DS_DESCRICAO", nullable = false, length = 250)
	private String descricao;
}
