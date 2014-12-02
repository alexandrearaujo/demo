package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe do modelo tipo receituário
 * 
 * @mv.uc - UC005 - Manter tipo de receituário
 * 
 * @author Carlos Roberto
 * @version 1 Date: 21/08/2014 15:00
 *
 */
@Entity
@Table(name="tipo_receituario")
public class TipoReceituario implements Serializable
{

    private static final long serialVersionUID = 2333299989748181269L;

    @Id
    @SequenceGenerator(name = "SEQ_TIPO_RECEITUARIO", sequenceName = "SEQ_TIPO_RECEITUARIO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_RECEITUARIO")
    @Column(name = "CD_TIPO_RECEITUARIO", length = 8, nullable = false)
    private Long id;
    
    @Column(name="DS_TIPO_RECEITUARIO", length = 150)
    private String descricao;
    
    @Column(name="TP_COR_RECEITUARIO", length = 1)
    private Long cor;
    
    @Column(name="NR_DIAS_VALIDADE_RECEITA", length = 3)
    private Long numeroDiasValidadeReceita;
    
    @Column(name="QT_VIAS_RECEITA", length = 1)
    private Long quantidadeViaReceita;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public Long getCor()
    {
        return cor;
    }

    public void setCor(Long cor)
    {
        this.cor = cor;
    }

    public Long getNumeroDiasValidadeReceita()
    {
        return numeroDiasValidadeReceita;
    }

    public void setNumeroDiasValidadeReceita(Long numeroDiasValidadeReceita)
    {
        this.numeroDiasValidadeReceita = numeroDiasValidadeReceita;
    }

    public Long getQuantidadeViaReceita()
    {
        return quantidadeViaReceita;
    }

    public void setQuantidadeViaReceita(Long quantidadeViaReceita)
    {
        this.quantidadeViaReceita = quantidadeViaReceita;
    }
    
    
}
