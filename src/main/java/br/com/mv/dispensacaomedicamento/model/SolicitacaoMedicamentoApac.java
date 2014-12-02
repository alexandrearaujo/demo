package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe do modelo SolicitacaoMedicamentoApac
 * 
 * @mv.uc - UC03 - Manter Solicitacao Medicamento APAC
 * 
 * @author Carlos Roberto
 * @version 1 Date: 29/08/2014 16:00
 *
 */
@Entity
@Table(name="solicitacao_medcmto_apac")
@PrimaryKeyJoinColumn(name = "cd_solicitacao_mdcmt_apac", referencedColumnName = "cd_solicitacao_medicamento")
public class SolicitacaoMedicamentoApac extends SolicitacaoMedicamento implements Serializable
{

    private static final long serialVersionUID = -5317583589349208915L;

    @Column(name="nr_apac", length=13)
    private Long numeroApac;
    
    @Temporal(TemporalType.DATE)
    @Column(name="dt_inicio")
    private Date dataInicio;
    
    @Column(name="dt_fim")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    
    @Column(name="tp_hemofilia", length=1)
    private Long tipoHemofilia;
    
    @Column(name="tp_inibidor", length=1)
    private Long tipoInibidor;
    
    @ManyToOne
    @JoinColumn(name="cd_vinculo_profissional_autzd", referencedColumnName="cd_vinculo_profissional")
    private VinculoProfissionalSigas profissionalAutorizador;
    
    @ManyToOne
    @JoinColumn(name="cd_vinculo_profissional_exectt", referencedColumnName="cd_vinculo_profissional")
    private VinculoProfissionalSigas profissionalExecutante;
    
    @Column(name="nr_peso", length=3)
    private Long numeroPeso;
    
    @Column(name="nr_altura", length=3)
    private Long numeroAltura;
    
    @Column(name="sn_transplante", length=1)
    private Boolean transplante;
    
    @Column(name="qt_transplantes", length=2)
    private Long quantidadeTransplante;
    
    @Column(name="sn_gestante", length=1)
    private Boolean gestante;
    
    @ManyToOne
    @JoinColumn(name="cd_procedimento_cid_principal", referencedColumnName="cd_procedimento_cid")
    private ProcedimentoCid procedimentoCidPrincipal;

    @ManyToOne
    @JoinColumn(name="cd_procedimento_cid_secundario", referencedColumnName="cd_procedimento_cid")
    private ProcedimentoCid procedimentoCidSecundario;
    
    @Column(name="ds_observacao", length=570)
    private String observacao;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dh_autorizacao")
    private Date dataAutorizacao;

    @Temporal(TemporalType.DATE)
    @Column(name="dt_inicio_tratamento")
    private Date dataInicioTratamento;
    
    @ManyToOne
    @JoinColumn(name="cd_solicitacao_mdcmt_apac_antr")
    private SolicitacaoMedicamentoApac solicitacaoMedicamentoAnteriorApac;
    
    @Column(name="sn_renovada")
    private Boolean renovada;
    
    
    public SolicitacaoMedicamentoApac clone(SolicitacaoMedicamento solicitacaoMedicamento)
    {
        SolicitacaoMedicamentoApac solicitacaoMedicamentoApacClone = new SolicitacaoMedicamentoApac();
        
        solicitacaoMedicamentoApacClone.setId(solicitacaoMedicamento.getId());
        solicitacaoMedicamentoApacClone.setSituacaoSolicitacaoMedicamento(solicitacaoMedicamento.getSituacaoSolicitacaoMedicamento());
        solicitacaoMedicamentoApacClone.setCidadaoPaciente(solicitacaoMedicamento.getCidadaoPaciente());
        solicitacaoMedicamentoApacClone.setCodigoReceita(solicitacaoMedicamento.getCodigoReceita());
        solicitacaoMedicamentoApacClone.setVinculoProfissional(solicitacaoMedicamento.getVinculoProfissional());
        solicitacaoMedicamentoApacClone.setDataProximaConsulta(solicitacaoMedicamento.getDataProximaConsulta());
        solicitacaoMedicamentoApacClone.setFarmacia(solicitacaoMedicamento.getFarmacia());
        solicitacaoMedicamentoApacClone.setPrescricaoMedica(solicitacaoMedicamento.getPrescricaoMedica());
        solicitacaoMedicamentoApacClone.setUsuario(solicitacaoMedicamento.getUsuario());
        solicitacaoMedicamentoApacClone.setDataCadastro(solicitacaoMedicamento.getDataCadastro());
        solicitacaoMedicamentoApacClone.setApac(solicitacaoMedicamento.isApac());
        solicitacaoMedicamentoApacClone.setVersao(solicitacaoMedicamento.getVersao());
        
        return solicitacaoMedicamentoApacClone;
    }
    
    public Long getNumeroApac()
    {
        return numeroApac;
    }
    public void setNumeroApac(Long numeroApac)
    {
        this.numeroApac = numeroApac;
    }

    public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
    }
    public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim)
    {
        this.dataFim = dataFim;
    }
    public Long getTipoHemofilia()
    {
        return tipoHemofilia;
    }
    public void setTipoHemofilia(Long tipoHemofilia)
    {
        this.tipoHemofilia = tipoHemofilia;
    }
    public Long getTipoInibidor()
    {
        return tipoInibidor;
    }
    public void setTipoInibidor(Long tipoInibidor)
    {
        this.tipoInibidor = tipoInibidor;
    }
    public VinculoProfissionalSigas getProfissionalAutorizador()
    {
        return profissionalAutorizador;
    }
    public void setProfissionalAutorizador(VinculoProfissionalSigas profissionalAutorizador)
    {
        this.profissionalAutorizador = profissionalAutorizador;
    }
    public VinculoProfissionalSigas getProfissionalExecutante()
    {
        return profissionalExecutante;
    }
    public void setProfissionalExecutante(VinculoProfissionalSigas profissionalExecutante)
    {
        this.profissionalExecutante = profissionalExecutante;
    }
    public Long getNumeroPeso()
    {
        return numeroPeso;
    }
    public void setNumeroPeso(Long numeroPeso)
    {
        this.numeroPeso = numeroPeso;
    }
    public Long getNumeroAltura()
    {
        return numeroAltura;
    }
    public void setNumeroAltura(Long numeroAltura)
    {
        this.numeroAltura = numeroAltura;
    }
    public Long getQuantidadeTransplante()
    {
        return quantidadeTransplante;
    }
    public void setQuantidadeTransplante(Long quantidadeTransplante)
    {
        this.quantidadeTransplante = quantidadeTransplante;
    }
    public Boolean getGestante()
    {
        return gestante;
    }
    public void setGestante(Boolean gestante)
    {
        this.gestante = gestante;
    }
    public ProcedimentoCid getProcedimentoCidPrincipal()
    {
        return procedimentoCidPrincipal;
    }
    public void setProcedimentoCidPrincipal(ProcedimentoCid procedimentoCidPrincipal)
    {
        this.procedimentoCidPrincipal = procedimentoCidPrincipal;
    }
    public ProcedimentoCid getProcedimentoCidSecundario()
    {
        return procedimentoCidSecundario;
    }
    public void setProcedimentoCidSecundario(ProcedimentoCid procedimentoCidSecundario)
    {
        this.procedimentoCidSecundario = procedimentoCidSecundario;
    }
    public String getObservacao()
    {
        return observacao;
    }
    public void setObservacao(String observacao)
    {
        this.observacao = observacao;
    }
    public Boolean getTransplante()
    {
        return transplante;
    }
    public void setTransplante(Boolean transplante)
    {
        this.transplante = transplante;
    }

    public Date getDataAutorizacao() {
		return dataAutorizacao;
	}

	public void setDataAutorizacao(Date dataAutorizacao)
    {
        this.dataAutorizacao = dataAutorizacao;
    }


    public Date getDataInicioTratamento() {
		return dataInicioTratamento;
	}

	public void setDataInicioTratamento(Date dataInicioTratamento)
    {
        this.dataInicioTratamento = dataInicioTratamento;
    }
    
    public SolicitacaoMedicamentoApac getSolicitacaoMedicamentoAnteriorApac()
    {
        return solicitacaoMedicamentoAnteriorApac;
    }

    public void setSolicitacaoMedicamentoAnteriorApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoAnteriorApac)
    {
        this.solicitacaoMedicamentoAnteriorApac = solicitacaoMedicamentoAnteriorApac;
    }

    public Boolean getRenovada()
    {
        return renovada;
    }
    public void setRenovada(Boolean renovada)
    {
        this.renovada = renovada;
    }
}
