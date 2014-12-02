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
 * Classe do modelo {@link HistoricoSolicitacaoMedicamento}
 *
 * @mv.uc - UC03 - Manter Solicitacao Medicamento
 * 
 * @author Carlos Roberto
 * @version 1 Date: 29/08/2014 16:00
 *
 */
@Entity
@Table(name="historico_solicitacao_medcmnto")
public class HistoricoSolicitacaoMedicamento implements Serializable
{

    private static final long serialVersionUID = -6716467889245741971L;

    @Id
    @SequenceGenerator(name="seq_historico_solicit_mdcmt", sequenceName="seq_historico_solicit_mdcmt", allocationSize=0)
    @GeneratedValue(generator="seq_historico_solicit_mdcmt", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_historico_solicit_mdcmt", length=10)
    private Long id;
    
    @ManyToOne         
    @JoinColumn(name="cd_situacao_solicitacao_mdcmt")
    private SituacaoSolicitacaoMedicamento situacaoSolicitacaoMedicamento;
    
    @ManyToOne
    @JoinColumn(name="cd_solicitacao_medicamento")
    private SolicitacaoMedicamento solicitacaoMedicamento;
    
    @ManyToOne
    @JoinColumn(name="cd_usuario")
    private User usuario;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dh_cadastro")
    private Date dataCadastro;
    

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

    public SolicitacaoMedicamento getSolicitacaoMedicamento()
    {
        return solicitacaoMedicamento;
    }

    public void setSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento)
    {
        this.solicitacaoMedicamento = solicitacaoMedicamento;
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
