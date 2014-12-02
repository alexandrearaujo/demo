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
 * Classe do modelo MotivoDevolucaoMedicamento
 * 
 * @mv.uc - UC10 - Manter Devolução Medicamento
 * 
 * @author Ryan Fernandes
 * @version 1 Date: 01/09/2014 09:30
 *
 */
@Entity
@Table(name = "MOTIVO_DEVOLUCAO_MEDCMTO")
public class MotivoDevolucaoMedicamento implements Serializable
{

    @Id
    @SequenceGenerator(name = "SEQ_MOTIVO_DEVOLUCAO_MDCMT", sequenceName = "SEQ_MOTIVO_DEVOLUCAO_MDCMT")
    @GeneratedValue(generator = "SEQ_MOTIVO_DEVOLUCAO_MDCMT", strategy = GenerationType.SEQUENCE)
    @Column(name = "cd_motivo_devolucao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cd_usuario")
    private User usuario;

    @Column(name = "ds_motivo")
    private String descricao;
    
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

    public User getUsuario()
    {
        return usuario;
    }

    public void setUsuario(User usuario)
    {
        this.usuario = usuario;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }



    public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }

}
