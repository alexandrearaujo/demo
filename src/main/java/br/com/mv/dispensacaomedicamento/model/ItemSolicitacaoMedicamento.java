package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;
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

/**
 * Classe do modelo item solcitação medicamento
 * 
 * @author Carlos Roberto
 * @version 1 Date: 29/08/2014 16:00
 *
 */
@Entity
@Table(name="item_solicitacao_medicamento")
@Inheritance(strategy = InheritanceType.JOINED)
public class ItemSolicitacaoMedicamento implements Serializable
{
    private static final long serialVersionUID = -1095732081852579227L;

    @Id
    @SequenceGenerator(name="SEQ_ITEM_SOLICITACAO_MEDCMTO", sequenceName="SEQ_ITEM_SOLICITACAO_MEDCMTO")
    @GeneratedValue(generator="SEQ_ITEM_SOLICITACAO_MEDCMTO", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_item_solicitacao_medcmto")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="cd_solicitacao_medicamento")
    private SolicitacaoMedicamento solicitacaoMedicamento;
    
    @Column(name="sn_apac")
    private boolean apac;
    
    @Column(name="dh_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
                  
    @Column(name="qt_solicitada_total")
    private Long quantidadeSolicitadaTotal;
    
    @ManyToOne
    @JoinColumn(name="cd_usuario")
    private User usuario;
    
    @ManyToOne
    @JoinColumn(name="cd_medicamento_item_unidade")
    private MedicamentoItemUnidade medicamentoItemUnidade;
    
    @ManyToOne
    @JoinColumn(name="cd_tipo_frequencia")
    private TipoFrequencia tipoFrequencia;
    
    @Column(name="sn_tratamento_continuo")
    private boolean tratamentoContinuo;
    
    @Column(name="qt_periodo_tratamento")
    private Long quantidadePeriodoTratamento;
    
    @Column(name="tp_periodo_tratamento")
    private Long tipoPeriodoTratamento;

    @ManyToOne
    @JoinColumn(name="cd_via_administracao_medcmnto")
    private ViaAdministracaoMedicamento viaAdministracaoMedicamento;
    
    @Column(name="sn_cid_nao_padronizado")
    private boolean cidNaoPadronizado;
    
    @Column(name="ds_orientacao")
    private String orientacao;
    
    @Column(name="qt_dose")
    private Long quantidadeDose;
    
    public ItemSolicitacaoMedicamento clone(ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac)
    {
        ItemSolicitacaoMedicamento itemSolicitacaoMedicamentoClone = new ItemSolicitacaoMedicamento();
        
        itemSolicitacaoMedicamentoClone.setId(itemSolicitacaoMedicamentoApac.getId());
        itemSolicitacaoMedicamentoClone.setSolicitacaoMedicamento(itemSolicitacaoMedicamentoApac.getSolicitacaoMedicamento());
        itemSolicitacaoMedicamentoClone.setApac(itemSolicitacaoMedicamentoApac.isApac());
        itemSolicitacaoMedicamentoClone.setDataCadastro(itemSolicitacaoMedicamentoApac.getDataCadastro());
        itemSolicitacaoMedicamentoClone.setQuantidadeDose(itemSolicitacaoMedicamentoApac.getQuantidadeDose());
        itemSolicitacaoMedicamentoClone.setQuantidadeSolicitadaTotal(itemSolicitacaoMedicamentoApac.getQuantidadeSolicitadaTotal());
        itemSolicitacaoMedicamentoClone.setUsuario(itemSolicitacaoMedicamentoApac.getUsuario());
        itemSolicitacaoMedicamentoClone.setMedicamentoItemUnidade(itemSolicitacaoMedicamentoApac.getMedicamentoItemUnidade());
        itemSolicitacaoMedicamentoClone.setTipoFrequencia(itemSolicitacaoMedicamentoApac.getTipoFrequencia());
        itemSolicitacaoMedicamentoClone.setTratamentoContinuo(itemSolicitacaoMedicamentoApac.isTratamentoContinuo());
        itemSolicitacaoMedicamentoClone.setQuantidadePeriodoTratamento(itemSolicitacaoMedicamentoApac.getQuantidadePeriodoTratamento());
        itemSolicitacaoMedicamentoClone.setTipoPeriodoTratamento(itemSolicitacaoMedicamentoApac.getTipoPeriodoTratamento());
        itemSolicitacaoMedicamentoClone.setViaAdministracaoMedicamento(itemSolicitacaoMedicamentoApac.getViaAdministracaoMedicamento());
        itemSolicitacaoMedicamentoClone.setCidNaoPadronizado(itemSolicitacaoMedicamentoApac.isCidNaoPadronizado());
        itemSolicitacaoMedicamentoClone.setOrientacao(itemSolicitacaoMedicamentoApac.getOrientacao());
        
        return itemSolicitacaoMedicamentoClone;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public SolicitacaoMedicamento getSolicitacaoMedicamento()
    {
        return solicitacaoMedicamento;
    }

    public void setSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento)
    {
        this.solicitacaoMedicamento = solicitacaoMedicamento;
    }

    public Date getDataCadastro()
    {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro)
    {
        this.dataCadastro = dataCadastro;
    }

    public Long getQuantidadeSolicitadaTotal()
    {
        return quantidadeSolicitadaTotal;
    }

    public void setQuantidadeSolicitadaTotal(Long quantidadeSolicitadaTotal)
    {
        this.quantidadeSolicitadaTotal = quantidadeSolicitadaTotal;
    }

    public User getUsuario()
    {
        return usuario;
    }

    public void setUsuario(User usuario)
    {
        this.usuario = usuario;
    }

    public MedicamentoItemUnidade getMedicamentoItemUnidade()
    {
        return medicamentoItemUnidade;
    }

    public void setMedicamentoItemUnidade(MedicamentoItemUnidade medicamentoItemUnidade)
    {
        this.medicamentoItemUnidade = medicamentoItemUnidade;
    }

    public TipoFrequencia getTipoFrequencia()
    {
        return tipoFrequencia;
    }

    public void setTipoFrequencia(TipoFrequencia tipoFrequencia)
    {
        this.tipoFrequencia = tipoFrequencia;
    }

    public Long getQuantidadePeriodoTratamento()
    {
        return quantidadePeriodoTratamento;
    }

    public void setQuantidadePeriodoTratamento(Long quantidadePeriodoTratamento)
    {
        this.quantidadePeriodoTratamento = quantidadePeriodoTratamento;
    }

    public Long getTipoPeriodoTratamento()
    {
        return tipoPeriodoTratamento;
    }

    public void setTipoPeriodoTratamento(Long tipoPeriodoTratamento)
    {
        this.tipoPeriodoTratamento = tipoPeriodoTratamento;
    }

    public ViaAdministracaoMedicamento getViaAdministracaoMedicamento()
    {
        return viaAdministracaoMedicamento;
    }

    public void setViaAdministracaoMedicamento(ViaAdministracaoMedicamento viaAdministracaoMedicamento)
    {
        this.viaAdministracaoMedicamento = viaAdministracaoMedicamento;
    }

    public boolean isApac()
    {
        return apac;
    }

    public void setApac(boolean apac)
    {
        this.apac = apac;
    }

    public boolean isTratamentoContinuo()
    {
        return tratamentoContinuo;
    }

    public void setTratamentoContinuo(boolean tratamentoContinuo)
    {
        this.tratamentoContinuo = tratamentoContinuo;
    }

    public boolean isCidNaoPadronizado()
    {
        return cidNaoPadronizado;
    }

    public void setCidNaoPadronizado(boolean cidNaoPadronizado)
    {
        this.cidNaoPadronizado = cidNaoPadronizado;
    }

    public String getOrientacao()
    {
        return orientacao;
    }

    public void setOrientacao(String orientacao)
    {
        this.orientacao = orientacao;
    }

    public Long getQuantidadeDose()
    {
        return quantidadeDose;
    }

    public void setQuantidadeDose(Long quantidadeDose)
    {
        this.quantidadeDose = quantidadeDose;
    }
    
}
