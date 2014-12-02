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
 * Classe do modelo {@link MotivoRejeicaoCancelamento}
 *
 * @mv.uc - UC03 - Manter Solicitacao Medicamento
 * 
 * @author Carlos Roberto
 * @version 1 Date: 17/10/2014 11:20
 *
 */
@Entity
@Table(name="motivo_rejeicao_cancelamento")
public class MotivoRejeicaoCancelamento implements Serializable
{

    private static final long serialVersionUID = -2033361261562615745L;
    
    @Id
    @SequenceGenerator(name="seq_motivo_rejeicao_cancel", sequenceName="seq_motivo_rejeicao_cancel", allocationSize=0)
    @GeneratedValue(generator="seq_motivo_rejeicao_cancel", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_motivo_rejeicao_cancel", length=10)
    private Long id;
    
    @Column(name="ds_motivo_rejeicao_cancel", length=50)
    private String descricaoCancelamento;
    
    @Column(name="sn_ativo")
    private boolean ativo;
    
    @Column(name="tp_motivo")
    private Long tipoMotivo;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescricaoCancelamento()
    {
        return descricaoCancelamento;
    }

    public void setDescricaoCancelamento(String descricaoCancelamento)
    {
        this.descricaoCancelamento = descricaoCancelamento;
    }

    public boolean isAtivo()
    {
        return ativo;
    }

    public void setAtivo(boolean ativo)
    {
        this.ativo = ativo;
    }

    public Long getTipoMotivo()
    {
        return tipoMotivo;
    }

    public void setTipoMotivo(Long tipoMotivo)
    {
        this.tipoMotivo = tipoMotivo;
    }
    
}
