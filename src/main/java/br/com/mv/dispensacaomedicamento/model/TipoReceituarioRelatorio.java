package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe do modelo tipo receituário relatório
 * 
 * @mv.uc - UC005 - Manter tipo de receituário
 * 
 * @author Carlos Roberto
 * @version 1 Date: 25/08/2014 10:01
 *
 */
@Entity
@Table(name="tipo_receituario_relatorio")
public class TipoReceituarioRelatorio implements Serializable
{
    private static final long serialVersionUID = -6477817772663579260L;

    @Id
    @SequenceGenerator(name = "SEQ_TIPO_RECEITUARIO_RELATORIO", sequenceName = "SEQ_TIPO_RECEITUARIO_RELATORIO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_RECEITUARIO_RELATORIO")
    @Column(name = "CD_TIPO_RECEITUARIO_RELATORIO", length = 6, nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="CD_TIPO_RECEITUARIO")
    private TipoReceituario tipoReceituario;
    
    @ManyToOne
    @JoinColumn(name="CD_TIPO_RELATORIO")
    private TipoRelatorio tipoRelatorio;
    
    @Column(name="SN_ATIVO")
    private boolean ativo; 

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public TipoReceituario getTipoReceituario()
    {
        return tipoReceituario;
    }

    public void setTipoReceituario(TipoReceituario tipoReceituario)
    {
        this.tipoReceituario = tipoReceituario;
    }

    public TipoRelatorio getTipoRelatorio()
    {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(TipoRelatorio tipoRelatorio)
    {
        this.tipoRelatorio = tipoRelatorio;
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
