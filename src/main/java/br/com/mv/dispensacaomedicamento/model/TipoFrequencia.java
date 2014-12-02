/**
 * 
 */
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author joao.franco
 * Classe de modelo da entidade TipoFrequencia
 * 
 */

@Entity
@Table(name="tipo_frequencia")
public class TipoFrequencia implements Serializable
{

    private static final long serialVersionUID = 2201431834062815952L;

    @Id
    @SequenceGenerator(name="SEQ_TIPO_FREQUENCIA", sequenceName = "SEQ_TIPO_FREQUENCIA", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_TIPO_FREQUENCIA" )
    @Column(name="CD_TIPO_FREQUENCIA",length = 8, nullable = false)
    private Long id;
    
    @Column(name="DS_TIPO_FREQUENCIA")
    private String descricaoFrequencia;
    
    @Column(name="DS_IMPRESSAO_RECEITA")
    private String impressaoReceita;
    
    @Column(name="NR_PERIODICIDADE")
    private Long periodicidade;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "HR_INICIAL")
    private Date horarioInicial;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescricaoFrequencia()
    {
        return descricaoFrequencia;
    }

    public void setDescricaoFrequencia(String descricaoFrequencia)
    {
        this.descricaoFrequencia = descricaoFrequencia;
    }

    public String getImpressaoReceita()
    {
        return impressaoReceita;
    }

    public void setImpressaoReceita(String impressaoReceita)
    {
        this.impressaoReceita = impressaoReceita;
    }

    public Long getPeriodicidade()
    {
        return periodicidade;
    }

    public void setPeriodicidade(Long periodicidade)
    {
        this.periodicidade = periodicidade;
    }



    public Date getHorarioInicial() {
		return horarioInicial;
	}

	public void setHorarioInicial(Date horarioInicial)
    {
        this.horarioInicial = horarioInicial;
    }

}
