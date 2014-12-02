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
 * Classe do modelo farmacia
 * 
 * @author Carlos Roberto
 * @version 1 Date: 01/09/2014 11:40
 *
 */
@Entity
@Table(name="farmacia")
public class Farmacia implements Serializable
{
    
    private static final long serialVersionUID = -7643553665680619814L;

    @Id
    @SequenceGenerator(name="SEQ_FARMACIA", sequenceName="SEQ_FARMACIA")
    @GeneratedValue(generator="SEQ_FARMACIA", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_farmacia", length=10)
    private Long id;
    
    @Column(name="ds_farmacia", length=100)
    private String descricaoFarmacia;
   
    @ManyToOne
    @JoinColumn(name="cd_estabelecimento_set_estblm")
    private EstabelecimentoSetorEstabelecimento estabelecimentoSetorEstabelecimento;
      
    @Column(name="nr_crf", length=7)
    private Long numeroCRF;
    
    @ManyToOne
    @JoinColumn(name="cd_vinculo_profissional")
    private VinculoProfissionalSigas farmaceuticoResponsavel;
    
    @Column(name="sn_numeracao_receita_auto", length=1)
    private boolean numeracaoReceitaAuto;
    
    @Column(name="sn_dispensa_cid_incompativel", length=1)
    private boolean dispensacaoCidIncompativel;
    
    @Column(name="sn_alerta_paciente_internado", length=1)
    private boolean alertaPacienteInternado;
    
    @Column(name="sn_fatura_apac", length=1)
    private boolean faturaAPAC;
    
    @Column(name="sn_paciente_bloqueado_ses", length=1)
    private boolean pacienteBloqueadoSES;
    
    @ManyToOne
    @JoinColumn(name="cd_usuario")
    private User usuario;
    
    @Column(name="dh_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDescricaoFarmacia()
    {
        return descricaoFarmacia;
    }

    public void setDescricaoFarmacia(String descricaoFarmacia)
    {
        this.descricaoFarmacia = descricaoFarmacia;
    }

    public Long getNumeroCRF()
    {
        return numeroCRF;
    }

    public void setNumeroCRF(Long numeroCRF)
    {
        this.numeroCRF = numeroCRF;
    }

    

    public VinculoProfissionalSigas getFarmaceuticoResponsavel()
    {
        return farmaceuticoResponsavel;
    }

    public void setFarmaceuticoResponsavel(VinculoProfissionalSigas farmaceuticoResponsavel)
    {
        this.farmaceuticoResponsavel = farmaceuticoResponsavel;
    }

    public boolean isNumeracaoReceitaAuto()
    {
        return numeracaoReceitaAuto;
    }

    public void setNumeracaoReceitaAuto(boolean numeracaoReceitaAuto)
    {
        this.numeracaoReceitaAuto = numeracaoReceitaAuto;
    }

    public boolean isDispensacaoCidIncompativel()
    {
        return dispensacaoCidIncompativel;
    }

    public void setDispensacaoCidIncompativel(boolean dispensacaoCidIncompativel)
    {
        this.dispensacaoCidIncompativel = dispensacaoCidIncompativel;
    }

    public boolean isAlertaPacienteInternado()
    {
        return alertaPacienteInternado;
    }

    public void setAlertaPacienteInternado(boolean alertaPacienteInternado)
    {
        this.alertaPacienteInternado = alertaPacienteInternado;
    }

    public boolean isFaturaAPAC()
    {
        return faturaAPAC;
    }

    public void setFaturaAPAC(boolean faturaAPAC)
    {
        this.faturaAPAC = faturaAPAC;
    }

    public boolean isPacienteBloqueadoSES()
    {
        return pacienteBloqueadoSES;
    }

    public void setPacienteBloqueadoSES(boolean pacienteBloqueadoSES)
    {
        this.pacienteBloqueadoSES = pacienteBloqueadoSES;
    }

    public User getUsuario()
    {
        return usuario;
    }

    public void setUsuario(User usuario)
    {
        this.usuario = usuario;
    }

    public Date getDataCadastro()
    {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }

    public EstabelecimentoSetorEstabelecimento getEstabelecimentoSetorEstabelecimento()
    {
        return estabelecimentoSetorEstabelecimento;
    }

    public void setEstabelecimentoSetorEstabelecimento(EstabelecimentoSetorEstabelecimento estabelecimentoSetorEstabelecimento)
    {
        this.estabelecimentoSetorEstabelecimento = estabelecimentoSetorEstabelecimento;
    }
    
    

}
