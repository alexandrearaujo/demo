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
 * Classe do modelo via administracao medicamento
 * 
 * @mv.uc - UC03 - Manter Solicitacao Medicamento APAC
 * 
 * @author Carlos Roberto
 * @version 1 Date: 29/08/2014 16:00
 *
 */
@Entity
@Table(name="via_administracao_medicamento")
public class ViaAdministracaoMedicamento implements Serializable
{
    private static final long serialVersionUID = -2346273189803937834L;

    @Id
    @SequenceGenerator(name="SEQ_VIA_ADMINISTRACAO_MEDCMNTO", sequenceName="SEQ_VIA_ADMINISTRACAO_MEDCMNTO")
    @GeneratedValue(generator="SEQ_VIA_ADMINISTRACAO_MEDCMNTO", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_via_administracao_medcmnto")
    private Long id;
    
    @Column(name="ds_via_administracao_medcmnto")
    private String descricao;
    
    @Column(name="sn_ativo")
    private boolean ativo;
    
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
    public boolean isAtivo()
    {
        return ativo;
    }
    public void setAtivo(boolean ativo)
    {
        this.ativo = ativo;
    }
}
