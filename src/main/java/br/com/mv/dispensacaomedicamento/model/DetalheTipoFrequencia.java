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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author joao.franco
 *
 */


@Entity
@Table(name="DETALHE_TIPO_FREQUENCIA")
public class DetalheTipoFrequencia implements Serializable
{

    
    private static final long serialVersionUID = 612841440511378320L;

    @Id
    @SequenceGenerator(name = "SEQ_DETALHE_TIPO_FREQUENCIA", sequenceName = "SEQ_DETALHE_TIPO_FREQUENCIA", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DETALHE_TIPO_FREQUENCIA")
    @Column(name = "CD_DETALHE_TIPO_FREQUENCIA", length = 6, nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name= "CD_TIPO_FREQUENCIA")
    private TipoFrequencia tipoFrequencia;
    
    @Column(name = "DS_MEDICACAO")
    private String descricaoMedicamento;
    
    @Column (name = "HR_MEDICACAO")
    private Date horaMedicacao;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public TipoFrequencia getTipoFrequencia()
    {
        return tipoFrequencia;
    }

    public void setTipoFrequencia(TipoFrequencia tipoFrequencia)
    {
        this.tipoFrequencia = tipoFrequencia;
    }

    public String getDescricaoMedicamento()
    {
        return descricaoMedicamento;
    }

    public void setDescricaoMedicamento(String descricaoMedicamento)
    {
        this.descricaoMedicamento = descricaoMedicamento;
    }

    public void setHoraMedicacao(Date horaMedicacao)
    {
        this.horaMedicacao = horaMedicacao;
    }

	public Date getHoraMedicacao() {
		return horaMedicacao;
	}
    
}
