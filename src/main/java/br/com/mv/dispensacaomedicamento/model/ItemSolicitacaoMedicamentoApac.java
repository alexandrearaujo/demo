package br.com.mv.dispensacaomedicamento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="item_solicitacao_medcmto_apac")
@PrimaryKeyJoinColumn(name = "cd_item_solicitacao_mdcmt_apac", referencedColumnName = "cd_item_solicitacao_medcmto")
public class ItemSolicitacaoMedicamentoApac extends ItemSolicitacaoMedicamento
{
    private static final long serialVersionUID = -3945979721381041915L;

    
    @ManyToOne
    @JoinColumn(name="cd_cid_nao_padronizado")
    private Cid codicoCidNaoPadronizado;

    @ManyToOne
    @JoinColumn(name="cd_procedimento_cid_principal")
    private ProcedimentoCid procedimentoCidPrincipal;
    
    @ManyToOne
    @JoinColumn(name="cd_procedimento_cid_secundario")
    private ProcedimentoCid procedimentoCidSecundario;
    
    @Column(name="qt_solicitada_mes_1")
    private Long quantidadeSolicitadaMes1;
    
    @Column(name="qt_solicitada_mes_2")
    private Long quantidadeSolicitadaMes2;
    
    @Column(name="qt_solicitada_mes_3")
    private Long quantidadeSolicitadaMes3;
    
    
    public ItemSolicitacaoMedicamentoApac clone (ItemSolicitacaoMedicamento itemSolicitacaoMedicamento)
    {
        ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApacClone = new ItemSolicitacaoMedicamentoApac();

        itemSolicitacaoMedicamentoApacClone.setId(itemSolicitacaoMedicamento.getId());
        itemSolicitacaoMedicamentoApacClone.setSolicitacaoMedicamento(itemSolicitacaoMedicamento.getSolicitacaoMedicamento());
        itemSolicitacaoMedicamentoApacClone.setApac(itemSolicitacaoMedicamento.isApac());
        itemSolicitacaoMedicamentoApacClone.setDataCadastro(itemSolicitacaoMedicamento.getDataCadastro());
        itemSolicitacaoMedicamentoApacClone.setQuantidadeDose(itemSolicitacaoMedicamento.getQuantidadeDose());
        itemSolicitacaoMedicamentoApacClone.setQuantidadeSolicitadaTotal(itemSolicitacaoMedicamento.getQuantidadeSolicitadaTotal());
        itemSolicitacaoMedicamentoApacClone.setUsuario(itemSolicitacaoMedicamento.getUsuario());
        itemSolicitacaoMedicamentoApacClone.setMedicamentoItemUnidade(itemSolicitacaoMedicamento.getMedicamentoItemUnidade());
        itemSolicitacaoMedicamentoApacClone.setTipoFrequencia(itemSolicitacaoMedicamento.getTipoFrequencia());
        itemSolicitacaoMedicamentoApacClone.setTratamentoContinuo(itemSolicitacaoMedicamento.isTratamentoContinuo());
        itemSolicitacaoMedicamentoApacClone.setQuantidadePeriodoTratamento(itemSolicitacaoMedicamento.getQuantidadePeriodoTratamento());
        itemSolicitacaoMedicamentoApacClone.setTipoPeriodoTratamento(itemSolicitacaoMedicamento.getTipoPeriodoTratamento());
        itemSolicitacaoMedicamentoApacClone.setViaAdministracaoMedicamento(itemSolicitacaoMedicamento.getViaAdministracaoMedicamento());
        itemSolicitacaoMedicamentoApacClone.setCidNaoPadronizado(itemSolicitacaoMedicamento.isCidNaoPadronizado());
        itemSolicitacaoMedicamentoApacClone.setOrientacao(itemSolicitacaoMedicamento.getOrientacao());
        
        return itemSolicitacaoMedicamentoApacClone;
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

    public Long getQuantidadeSolicitadaMes1()
    {
        return quantidadeSolicitadaMes1;
    }

    public void setQuantidadeSolicitadaMes1(Long quantidadeSolicitadaMes1)
    {
        this.quantidadeSolicitadaMes1 = quantidadeSolicitadaMes1;
    }

    public Long getQuantidadeSolicitadaMes2()
    {
        return quantidadeSolicitadaMes2;
    }

    public void setQuantidadeSolicitadaMes2(Long quantidadeSolicitadaMes2)
    {
        this.quantidadeSolicitadaMes2 = quantidadeSolicitadaMes2;
    }

    public Long getQuantidadeSolicitadaMes3()
    {
        return quantidadeSolicitadaMes3;
    }

    public void setQuantidadeSolicitadaMes3(Long quantidadeSolicitadaMes3)
    {
        this.quantidadeSolicitadaMes3 = quantidadeSolicitadaMes3;
    }

    public Cid getCodicoCidNaoPadronizado()
    {
        return codicoCidNaoPadronizado;
    }

    public void setCodicoCidNaoPadronizado(Cid codicoCidNaoPadronizado)
    {
        this.codicoCidNaoPadronizado = codicoCidNaoPadronizado;
    }
    
}
