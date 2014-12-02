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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe do modelo ItemDevolucaoMedicamento
 * 
 * @mv.uc - UC10 - Manter Devolução Medicamento
 * 
 * @author Ryan Fernandes
 * @version 1 Date: 01/09/2014 09:30
 *
 */
@Entity
@Table(name = "ITEM_DEVOLUCAO_MDCMT")
public class ItemDevolucaoMedicamento implements Serializable
{

    @Id
    @SequenceGenerator(name = "SEQ_ITEM_DEVOLUCAO_MEDCMTO", sequenceName = "SEQ_ITEM_DEVOLUCAO_MEDCMTO")
    @GeneratedValue(generator = "SEQ_ITEM_DEVOLUCAO_MEDCMTO", strategy = GenerationType.SEQUENCE)
    @Column(name = "cd_item_devolucao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cd_devolucao_medicamento")
    private DevolucaoMedicamento devolucaoMedicamento;
    
    @OneToOne
    @JoinColumn(name="cd_medicamento", referencedColumnName = "cd_medicamento", insertable=false, updatable=false)
    private Medicamento medicamento;

    @Column(name="dt_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public DevolucaoMedicamento getDevolucaoMedicamento()
    {
        return devolucaoMedicamento;
    }

    public void setDevolucaoMedicamento(DevolucaoMedicamento devolucaoMedicamento)
    {
        this.devolucaoMedicamento = devolucaoMedicamento;
    }

    public Medicamento getMedicamento()
    {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento)
    {
        this.medicamento = medicamento;
    }

    public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }
    
}
