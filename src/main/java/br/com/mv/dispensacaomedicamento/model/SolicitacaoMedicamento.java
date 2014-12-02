package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Classe do modelo solicitação de medicamento
 * 
 * @mv.uc - UC03 - Manter Solicitacao Medicamento APAC
 * 
 * @author Carlos Roberto
 * @version 1 Date: 29/08/2014 16:00
 *
 */
@Entity
@Table(name="solicitacao_medicamento")
@Inheritance(strategy = InheritanceType.JOINED)
public class SolicitacaoMedicamento implements Serializable
{
    
    private static final long serialVersionUID = -6362578296256872406L;

    @Id
    @SequenceGenerator(name="SEQ_SOLICITACAO_MEDICAMENTO", sequenceName="SEQ_SOLICITACAO_MEDICAMENTO", allocationSize=0)
    @GeneratedValue(generator="SEQ_SOLICITACAO_MEDICAMENTO", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_solicitacao_medicamento", length=10)
    private Long id;
    
    @ManyToOne         
    @JoinColumn(name="cd_situacao_solicitacao_mdcmt")
    private SituacaoSolicitacaoMedicamento situacaoSolicitacaoMedicamento;
    
    @ManyToOne
    @JoinColumn(name="cd_cidadao_paciente", referencedColumnName="CD_CIDADAO")
    private Cidadao cidadaoPaciente;
    
    @Column(name="ds_codigo_receita", length=10)
    private String codigoReceita;
    
    @ManyToOne
    @JoinColumn(name="cd_vinculo_profissional_prsctr")
    private VinculoProfissionalSigas vinculoProfissional;
                                    
    @Column(name="dt_proxima_consulta")
    @Temporal(TemporalType.DATE)
    private Date dataProximaConsulta;
    
    @ManyToOne
    @JoinColumn(name="cd_farmacia")
    private Farmacia farmacia;
    
    @Column(name="sn_prescricao_medica")
    private Boolean prescricaoMedica;
    
    @ManyToOne
    @JoinColumn(name="cd_usuario")
    private User usuario;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dh_cadastro")
    private Date dataCadastro;
    
    @Version
    @Column(name="nr_versao")
    private Long versao;
    
    @Column(name="sn_apac")
    private boolean apac;
    
    @Transient
    private Collection<ItemSolicitacaoMedicamentoApac> listaItemSolicitacaoMedicamentosApac;
    
    public SolicitacaoMedicamento clone(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        SolicitacaoMedicamento solicitacaoMedicamentoClone = new SolicitacaoMedicamento();
        
        solicitacaoMedicamentoClone.setId(solicitacaoMedicamentoApac.getId());
        solicitacaoMedicamentoClone.setSituacaoSolicitacaoMedicamento(solicitacaoMedicamentoApac.getSituacaoSolicitacaoMedicamento());
        solicitacaoMedicamentoClone.setCidadaoPaciente(solicitacaoMedicamentoApac.getCidadaoPaciente());
        solicitacaoMedicamentoClone.setCodigoReceita(solicitacaoMedicamentoApac.getCodigoReceita());
        solicitacaoMedicamentoClone.setVinculoProfissional(solicitacaoMedicamentoApac.getVinculoProfissional());
        solicitacaoMedicamentoClone.setDataProximaConsulta(solicitacaoMedicamentoApac.getDataProximaConsulta());
        solicitacaoMedicamentoClone.setFarmacia(solicitacaoMedicamentoApac.getFarmacia());
        solicitacaoMedicamentoClone.setPrescricaoMedica(solicitacaoMedicamentoApac.getPrescricaoMedica());
        solicitacaoMedicamentoClone.setUsuario(solicitacaoMedicamentoApac.getUsuario());
        solicitacaoMedicamentoClone.setDataCadastro(solicitacaoMedicamentoApac.getDataCadastro());
        solicitacaoMedicamentoClone.setApac(solicitacaoMedicamentoApac.isApac());
        solicitacaoMedicamentoClone.setVersao(solicitacaoMedicamentoApac.getVersao());
        
        return solicitacaoMedicamentoClone;
    }
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public SituacaoSolicitacaoMedicamento getSituacaoSolicitacaoMedicamento()
    {
        return situacaoSolicitacaoMedicamento;
    }
    public void setSituacaoSolicitacaoMedicamento(SituacaoSolicitacaoMedicamento situacaoSolicitacaoMedicamento)
    {
        this.situacaoSolicitacaoMedicamento = situacaoSolicitacaoMedicamento;
    }
    public Cidadao getCidadaoPaciente()
    {
        return cidadaoPaciente;
    }
    public void setCidadaoPaciente(Cidadao cidadaoPaciente)
    {
        this.cidadaoPaciente = cidadaoPaciente;
    }
    public String getCodigoReceita()
    {
        return codigoReceita;
    }
    public void setCodigoReceita(String codigoReceita)
    {
        this.codigoReceita = codigoReceita;
    }
    public VinculoProfissionalSigas getVinculoProfissional()
    {
        return vinculoProfissional;
    }
    public void setVinculoProfissional(VinculoProfissionalSigas vinculoProfissional)
    {
        this.vinculoProfissional = vinculoProfissional;
    }

    public Date getDataProximaConsulta() {
		return dataProximaConsulta;
	}

	public void setDataProximaConsulta(Date dataProximaConsulta)
    {
        this.dataProximaConsulta = dataProximaConsulta;
    }
    public Farmacia getFarmacia()
    {
        return farmacia;
    }
    public void setFarmacia(Farmacia farmacia)
    {
        this.farmacia = farmacia;
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
    
    public Boolean getPrescricaoMedica()
    {
        return prescricaoMedica;
    }

    public void setPrescricaoMedica(Boolean prescricaoMedica)
    {
        this.prescricaoMedica = prescricaoMedica;
    }

    public Long getVersao()
    {
        return versao;
    }
    public void setVersao(Long versao)
    {
        this.versao = versao;
    }
    public Collection<ItemSolicitacaoMedicamentoApac> getListaItemSolicitacaoMedicamentosApac()
    {
        return listaItemSolicitacaoMedicamentosApac;
    }
    public void setListaItemSolicitacaoMedicamentosApac(Collection<ItemSolicitacaoMedicamentoApac> listaItemSolicitacaoMedicamentosApac)
    {
        this.listaItemSolicitacaoMedicamentosApac = listaItemSolicitacaoMedicamentosApac;
    }
    public boolean isApac()
    {
        return apac;
    }
    public void setApac(boolean apac)
    {
        this.apac = apac;
    }
    
    
    
}
