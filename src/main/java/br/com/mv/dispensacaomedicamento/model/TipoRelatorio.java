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
 * Classe do modelo tipo de relatório
 * 
 * @mv.uc - UC005 - Manter tipo de receituário
 * 
 * @author Carlos Roberto
 * @version 1 Date: 25/08/2014 10:00
 *
 */
@Entity
@Table(name="tipo_relatorio")
public class TipoRelatorio implements Serializable
{
    private static final long serialVersionUID = -4095819741246517017L;

    @Id
    @SequenceGenerator(name = "SEQ_TIPO_RELATORIO", sequenceName = "SEQ_TIPO_RELATORIO", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_RELATORIO")
    @Column(name = "CD_TIPO_RELATORIO", length = 6, nullable = false)
    private Long id;
    
    @Column(name="NM_TIPO_RELATORIO")
    private String nomeTipoRelatorio;
    
    @Column(name="DS_TIPO_RELATORIO")
    private String descricaoTipoRelatorio;

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getNomeTipoRelatorio()
    {
        return nomeTipoRelatorio;
    }
    public void setNomeTipoRelatorio(String nomeTipoRelatorio)
    {
        this.nomeTipoRelatorio = nomeTipoRelatorio;
    }
    public String getDescricaoTipoRelatorio()
    {
        return descricaoTipoRelatorio;
    }
    public void setDescricaoTipoRelatorio(String descricaoTipoRelatorio)
    {
        this.descricaoTipoRelatorio = descricaoTipoRelatorio;
    }
    
}
