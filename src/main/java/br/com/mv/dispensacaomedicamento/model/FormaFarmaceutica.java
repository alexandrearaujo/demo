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
 * Classe do modelo forma farmaceutica
 * 
 * @author Carlos Roberto
 * @version 1 Date: 01/09/2014 09:30
 *
 */
@Entity
@Table(name="forma_farmaceutica")
public class FormaFarmaceutica implements Serializable
{
    private static final long serialVersionUID = 5080425151963925496L;

    @Id
    @SequenceGenerator(name="SEQ_FORMA_FARMACEUTICA", sequenceName="SEQ_FORMA_FARMACEUTICA")
    @GeneratedValue(generator="SEQ_FORMA_FARMACEUTICA", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_forma_farmaceutica")
    private Long id;
    
    @Column(name="ds_forma_farmaceutica")
    private String descricao;
    
    @Column(name="sn_ativo")
    private Boolean ativo;

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
    public void setDescricao(String descricaoFormaFarmaceutica)
    {
        this.descricao = descricaoFormaFarmaceutica;
    }
    public Boolean isAtivo()
    {
        return ativo;
    }
    public void setAtivo(Boolean ativo)
    {
        this.ativo = ativo;
    }

}

