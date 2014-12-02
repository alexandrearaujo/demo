package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.mv.dispensacaomedicamento.model.dicionario.EnumCaracteristica;
import br.com.mv.dispensacaomedicamento.model.dicionario.EnumLocal;


/**
 * Classe do modelo DevolucaoMedicamento
 * 
 * @mv.uc - UC10 - Manter Devolução Medicamento
 * 
 * @author Ryan Fernandes
 * @version 1 Date: 01/09/2014 09:30
 *
 */
@Entity
@Table(name="medicamento")
public class DevolucaoMedicamento implements Serializable
{

    
    @Id
    @SequenceGenerator(name="SEQ_MEDICAMENTO", sequenceName="SEQ_MEDICAMENTO")
    @GeneratedValue(generator="SEQ_MEDICAMENTO", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_medicamento")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "CD_CIDADAO", referencedColumnName = "CD_USUARIO")
    private User cidadao;
    
    @ManyToOne
    @JoinColumn(name = "cd_motivo_devolucao")
    private MotivoDevolucaoMedicamento motivo;
    
    @ManyToOne
    @JoinColumn(name = "CD_USUARIO", referencedColumnName = "CD_USUARIO")
    private User usuario;
    
    @Column(name="ds_observacao")
    private String observacao;
    
    @Column(name="tp_local")
    private Integer local;
    
    @Transient
    private EnumLocal enumLocal;
    
    @Column(name="tp_caracteristica")
    private Integer caracteristica;
    
    @Transient
    private EnumCaracteristica enumCaracteristica;
    
    @Column(name="dt_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    
    
    @OneToMany(mappedBy = "devolucaoMedicamento", targetEntity = ItemDevolucaoMedicamento.class, cascade = CascadeType.ALL, orphanRemoval = true )
    private Collection<ItemDevolucaoMedicamento> itens;


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public User getCidadao()
    {
        return cidadao;
    }


    public void setCidadao(User cidadao)
    {
        this.cidadao = cidadao;
    }


    public MotivoDevolucaoMedicamento getMotivo()
    {
        return motivo;
    }


    public void setMotivo(MotivoDevolucaoMedicamento motivo)
    {
        this.motivo = motivo;
    }


    public User getUsuario()
    {
        return usuario;
    }


    public void setUsuario(User usuario)
    {
        this.usuario = usuario;
    }


    public String getObservacao()
    {
        return observacao;
    }


    public void setObservacao(String observacao)
    {
        this.observacao = observacao;
    }


    public Integer getLocal()
    {
        return local;
    }


    public void setLocal(Integer local)
    {
        if(local == null)
            this.local = enumLocal.getcodigo();
        else
            this.local = local;
    }


    public EnumLocal getEnumLocal()
    {
        return enumLocal;
    }


    public void setEnumLocal(EnumLocal enumLocal)
    {
        this.enumLocal = enumLocal;
    }


    public Integer getCaracteristica()
    {
        return caracteristica;
    }


    public void setCaracteristica(Integer caracteristica)
    {
        if(caracteristica == null)
            this.caracteristica = enumCaracteristica.getId();
        else
            this.caracteristica = caracteristica; 
    }


    public EnumCaracteristica getEnumCaracteristica()
    {
        return enumCaracteristica;
    }


    public void setEnumCaracteristica(EnumCaracteristica enumCaracteristica)
    {
        this.enumCaracteristica = enumCaracteristica;
    }


    public Date getDataCadastro() {
		return dataCadastro;
	}


	public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }


    public Collection<ItemDevolucaoMedicamento> getItens()
    {
        return itens;
    }


    public void setItens(Collection<ItemDevolucaoMedicamento> itens)
    {
        this.itens = itens;
    }
    
    public void setTransientNull(){
        enumLocal = null;
        enumCaracteristica = null;
    }
    
    
}
