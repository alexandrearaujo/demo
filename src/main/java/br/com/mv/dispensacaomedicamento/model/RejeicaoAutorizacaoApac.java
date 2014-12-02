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
 * Classe do modelo {@link RejeicaoAutorizacaoApac}
 *
 * @mv.uc - UC03 - Manter Solicitacao Medicamento
 * 
 * @author Carlos Roberto
 * @version 1 Date: 17/10/2014 11:20
 *
 */
@Entity
@Table(name="rejeicao_autorizacao_apac")
public class RejeicaoAutorizacaoApac implements Serializable
{

    private static final long serialVersionUID = 2227313872183302139L;
    
    @Id
    @SequenceGenerator(name="seq_rejeicao_autorizacao_apac", sequenceName="seq_rejeicao_autorizacao_apac", allocationSize=0)
    @GeneratedValue(generator="seq_rejeicao_autorizacao_apac", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_rejeicao_autorizacao_apac", length=10)
    private Long id;
    
    @ManyToOne         
    @JoinColumn(name="cd_solicitacao_apac", referencedColumnName="cd_solicitacao_mdcmt_apac")
    private SolicitacaoMedicamentoApac solicitacaoMedicamentoApac;
    
    @ManyToOne
    @JoinColumn(name="cd_motivo_rejeicao_cancel")
    private MotivoRejeicaoCancelamento motivoRejeicaoCancelamento;
    
    @ManyToOne         
    @JoinColumn(name="cd_situacao_apac", referencedColumnName="cd_situacao_solicitacao_mdcmt")
    private SituacaoSolicitacaoMedicamento situacaoSolicitacaoMedicamento;
    
    @Column(name="ds_rejeicao_autorizacao_apac")
    private String descricaoRejeicaoAutorizacaoApac;
    
    @ManyToOne
    @JoinColumn(name="cd_usuario")
    private User usuario;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dh_rejeicao")
    private Date dataRejeicao;

    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public SolicitacaoMedicamentoApac getSolicitacaoMedicamentoApac()
    {
        return solicitacaoMedicamentoApac;
    }

    public void setSolicitacaoMedicamentoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        this.solicitacaoMedicamentoApac = solicitacaoMedicamentoApac;
    }

    public MotivoRejeicaoCancelamento getMotivoRejeicaoCancelamento()
    {
        return motivoRejeicaoCancelamento;
    }

    public void setMotivoRejeicaoCancelamento(MotivoRejeicaoCancelamento motivoRejeicaoCancelamento)
    {
        this.motivoRejeicaoCancelamento = motivoRejeicaoCancelamento;
    }

    public SituacaoSolicitacaoMedicamento getSituacaoSolicitacaoMedicamento()
    {
        return situacaoSolicitacaoMedicamento;
    }

    public void setSituacaoSolicitacaoMedicamento(SituacaoSolicitacaoMedicamento situacaoSolicitacaoMedicamento)
    {
        this.situacaoSolicitacaoMedicamento = situacaoSolicitacaoMedicamento;
    }

    public String getDescricaoRejeicaoAutorizacaoApac()
    {
        return descricaoRejeicaoAutorizacaoApac;
    }

    public void setDescricaoRejeicaoAutorizacaoApac(String descricaoRejeicaoAutorizacaoApac)
    {
        this.descricaoRejeicaoAutorizacaoApac = descricaoRejeicaoAutorizacaoApac;
    }

    public User getUsuario()
    {
        return usuario;
    }

    public void setUsuario(User usuario)
    {
        this.usuario = usuario;
    }



    public Date getDataRejeicao() {
		return dataRejeicao;
	}

	public void setDataRejeicao(Date dataRejeicao)
    {
        this.dataRejeicao = dataRejeicao;
    }
    
}
