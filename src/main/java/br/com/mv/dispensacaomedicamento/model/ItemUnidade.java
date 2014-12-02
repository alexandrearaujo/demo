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
 * Classe do modelo ItemUnidade
 * 
 * @mv.uc - UC03 - Manter Solicitacao Medicamento APAC
 * 
 * @author carlos.souza
 * @version 1 10:27 24/09/2014
 *
 */
@Entity
@Table(name="item_unidade")
public class ItemUnidade implements Serializable
{

    private static final long serialVersionUID = -2498641741890104334L;

    @Id
    @SequenceGenerator(name="SEQ_ITEM_UNIDADE", sequenceName="SEQ_ITEM_UNIDADE")
    @GeneratedValue(generator="SEQ_ITEM_UNIDADE", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_item_unidade")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="cd_unidade")
    private Unidade unidade;
    @Column(name="ds_sigla_item_unidade", length=10)
    private String descricaoSigla;
    @Column(name="ds_item_unidade", length=30)
    private String descricao;
    @Column(name="vl_fator")
    private Double valorFator;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dh_cadastro")
    private Date dataCadastro;
    @Column(name="sn_ativo")
    private boolean ativo;
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public Unidade getUnidade()
    {
        return unidade;
    }
    public void setUnidade(Unidade unidade)
    {
        this.unidade = unidade;
    }
    public String getDescricaoSigla()
    {
        return descricaoSigla;
    }
    public void setDescricaoSigla(String descricaoSigla)
    {
        this.descricaoSigla = descricaoSigla;
    }
    public String getDescricao()
    {
        return descricao;
    }
    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
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
    public boolean isAtivo()
    {
        return ativo;
    }
    public void setAtivo(boolean ativo)
    {
        this.ativo = ativo;
    }
   
    
}
