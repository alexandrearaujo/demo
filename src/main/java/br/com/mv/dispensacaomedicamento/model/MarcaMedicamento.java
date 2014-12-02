package br.com.mv.dispensacaomedicamento.model;

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
 * Classe do modelo marca medicamento
 * 
 * @mv.uc - UC012 - Manter Marca Medicamento Dispensação Medicamentos
 * 
 * @author Francisco Vernek
 * @version 1 Date: 13/10/2014 16:42
 *
 */

@Entity
@Table(name="marca_medicamento")
public class MarcaMedicamento
{    
    @Id
    @SequenceGenerator(name="SEQ_MARCA_MEDICAMENTO", sequenceName="SEQ_MARCA_MEDICAMENTO")
    @GeneratedValue(generator="SEQ_MARCA_MEDICAMENTO", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_marca_medicamento", length=10)
    private Long id;
    
    @Column(name="ds_marca_medicamento")
    private String descricao;
    
    @Column(name="sn_ativo")
    private boolean ativo;
    
    @ManyToOne
    @JoinColumn(name="cd_usuario")
    private User usuario;
    
    @Column(name="dh_cadastro")
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

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public boolean getAtivo()
    {
        return ativo;
    }

    public void setAtivo(boolean ativo)
    {
        this.ativo = ativo;
    }
    
    public User getUsuario()
    {
        return usuario;
    }

    public void setUsuario(User usuario)
    {
        this.usuario = usuario;
    }



    public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }

}
