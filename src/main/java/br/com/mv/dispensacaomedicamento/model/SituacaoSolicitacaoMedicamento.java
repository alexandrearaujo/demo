package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe do modelo situação solicitação de medicamento
 * 
 * @mv.uc - UC - Manter Solicitacao Medicamento
 * 
 * @author Carlos Roberto
 * @version 1 Date: 29/08/2014 16:00
 *
 */
@Entity
@Table(name="situacao_solicitacao_medcmnto")
public class SituacaoSolicitacaoMedicamento implements Serializable
{
    private static final long serialVersionUID = -8707425758217078606L;

    @Id
    @SequenceGenerator(name="SEQ_SITUACAO_SOLICITACAO_MDCMT",sequenceName="SEQ_SITUACAO_SOLICITACAO_MDCMT")
    @GeneratedValue(generator="SEQ_SITUACAO_SOLICITACAO_MDCMT", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_situacao_solicitacao_mdcmt", length=10)
    private Long id;
    
    @Column(name="ds_situacao_solicitacao_mdcmt", length=50)
    private String descricaoSituacaoMedicamento;
    
    @Column(name="sn_ativo", length=1)
    private boolean ativo;
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getDescricaoSituacaoMedicamento()
    {
        return descricaoSituacaoMedicamento;
    }
    public void setDescricaoSituacaoMedicamento(String descricao)
    {
        this.descricaoSituacaoMedicamento = descricao;
    }
    public boolean isAtivo()
    {
        return ativo;
    }
    public void setAtivo(boolean ativo)
    {
        this.ativo = ativo;
    }
    
}
