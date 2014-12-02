package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Calsse de modelo MedicamentoItemUnidade
 * 
 * @mv.uc - UC03 - Manter Solicitacao Medicamento APAC
 * 
 * @author carlos.souza
 * @version 1 08:50 24/09/2014
 *
 */
@Entity
@Table(name = "medicamento_item_unidade")
public class MedicamentoItemUnidade implements Serializable
{
    
    private static final long serialVersionUID = -3011078858765439614L;

    @Id
    @SequenceGenerator(name = "SEQ_MEDICAMENTO_ITEM_UNIDADE", sequenceName = "SEQ_MEDICAMENTO_ITEM_UNIDADE")
    @GeneratedValue(generator = "SEQ_MEDICAMENTO_ITEM_UNIDADE", strategy = GenerationType.SEQUENCE)
    @Column(name = "cd_medicamento_item_unidade")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cd_item_unidade")
    private ItemUnidade itemUnidade;
    @ManyToOne
    @JoinColumn(name = "cd_medicamento")
    private Medicamento medicamento;
    @Column(name = "ds_sigla_item_unidade")
    private String descricaoSiglaItem;
    @Column(name = "ds_sigla_unidade")
    private String descricaoSiglaUnidade;
    @Column(name = "ds_item_unidade")
    private String descricaoItemUnidade;
    @Column(name = "vl_fator")
    private Double valorFator;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_cadastro")
    private Date dataCadastro;
    @Column(name = "sn_ativo")
    private Boolean ativo;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public ItemUnidade getItemUnidade()
    {
        return itemUnidade;
    }

    public void setItemUnidade(ItemUnidade itemUnidade)
    {
        this.itemUnidade = itemUnidade;
    }

    public Medicamento getMedicamento()
    {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento)
    {
        this.medicamento = medicamento;
    }

    public String getDescricaoSiglaItem()
    {
        return descricaoSiglaItem;
    }

    public void setDescricaoSiglaItem(String descricaoSiglaItem)
    {
        this.descricaoSiglaItem = descricaoSiglaItem;
    }

    public String getDescricaoSiglaUnidade()
    {
        return descricaoSiglaUnidade;
    }

    public void setDescricaoSiglaUnidade(String descricaoSiglaUnidade)
    {
        this.descricaoSiglaUnidade = descricaoSiglaUnidade;
    }

    public String getDescricaoItemUnidade()
    {
        return descricaoItemUnidade;
    }

    public void setDescricaoItemUnidade(String descricaoItemUnidade)
    {
        this.descricaoItemUnidade = descricaoItemUnidade;
    }

    public Double getValorFator()
    {
        return valorFator;
    }

    public void setValorFator(Double valorFator)
    {
        this.valorFator = valorFator;
    }



    public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }

    public Boolean getAtivo()
    {
        return ativo;
    }

    public void setAtivo(Boolean ativo)
    {
        this.ativo = ativo;
    }

}
