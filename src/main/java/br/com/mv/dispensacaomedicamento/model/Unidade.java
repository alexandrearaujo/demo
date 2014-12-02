package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe do modelo Unidade 
 * 
 * @mv.uc - UC03 - Manter Solicitacao Medicamento APAC
 * 
 * @author carlos.souza
 * @version 1 10:00 24/09/2014
 *
 */
@Entity
@Table(name="unidade")
public class Unidade implements Serializable
{
    private static final long serialVersionUID = -4119486910272158490L;

    @Id
    @SequenceGenerator(name="SEQ_UNIDADE", sequenceName="SEQ_UNIDADE")
    @GeneratedValue(generator="SEQ_UNIDADE", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_unidade")
    private Long id;
    
    @Column(name="ds_sigla_unidade")
    private String descricaoSigla;
    @Column(name="ds_unidade")
    private String descricao;
    @Column(name="vl_fator")
    private Double valorFator;
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
    public Date getDataCadastro()
    {
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
