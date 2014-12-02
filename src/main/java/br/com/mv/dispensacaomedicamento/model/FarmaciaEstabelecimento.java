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
 * Classe do modelo solicitação de medicamento
 * 
 * @mv.uc - UC03 - Manter Solicitacao Medicamento APAC
 * 
 * @author Carlos Roberto
 * @version 1 Date: 29/08/2014 16:00
 *
 */
@Entity
@Table(name="farmacia_estabelecimento")
public class FarmaciaEstabelecimento implements Serializable
{

    private static final long serialVersionUID = -7665896963050060686L;

    @SequenceGenerator(name="SEQ_FARMACIA_ESTABELECIMENTO", sequenceName="SEQ_FARMACIA_ESTABELECIMENTO")
    @GeneratedValue(generator="SEQ_FARMACIA_ESTABELECIMENTO", strategy=GenerationType.SEQUENCE)
    @Column(name="CD_FARMACIA_ESTABELECIMENTO",length = 8, nullable = false)
    @Id
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="cd_estabelecimento")
    private Estabelecimento estabelecimento;
    
    @ManyToOne
    @JoinColumn(name="cd_farmacia")
    private Farmacia farmacia;

    @ManyToOne
    @JoinColumn(name="cd_usuario")
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_cadastro")
    private Date dataCadastro;
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public Estabelecimento getEstabelecimento()
    {
        return estabelecimento;
    }
    public void setEstabelecimento(Estabelecimento estabelecimento)
    {
        this.estabelecimento = estabelecimento;
    }
    public Farmacia getFarmacia()
    {
        return farmacia;
    }
    public void setFarmacia(Farmacia farmacia)
    {
        this.farmacia = farmacia;
    }
    public User getUser()
    {
        return user;
    }
    public void setUser(User user)
    {
        this.user = user;
    }

    public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }
    
}
