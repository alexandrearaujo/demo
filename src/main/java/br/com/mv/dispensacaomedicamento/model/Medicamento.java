package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe do modelo medicamento
 * 
 * @mv.uc - UC03 - Manter Solicitacao Medicamento APAC
 * 
 * @author Carlos Roberto
 * @version 1 Date: 01/09/2014 09:30
 *
 */
@Entity
@Table(name="medicamento")
public class Medicamento implements Serializable
{

    private static final long serialVersionUID = -137400529874069372L;

    @Id
    @SequenceGenerator(name="SEQ_MEDICAMENTO", sequenceName="SEQ_MEDICAMENTO")
    @GeneratedValue(generator="SEQ_MEDICAMENTO", strategy=GenerationType.SEQUENCE)
    @Column(name="cd_medicamento")
    private Long id;
    
    @Column(name="ds_medicamento")
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name="cd_tipo_receituario")
    private TipoReceituario tipoReceituario;
    
    @Column(name="sn_placebo")
    private Boolean placebo;
    
    @Column(name="sn_termolabel")
    private Boolean termolabel;
    
    @Column(name="tp_especifico_sexo")
    private Long tipoEspecificoSexo;
    
    @Column(name="tp_padronizado")
    private Long tipoPadronizado;
    
    @Column(name="sn_dispenca_retorno")
    private Boolean dispensaRetorno;
    
    @Column(name="sn_fraciona_embalagem")
    private Boolean fracionaEmbalagem;
    
    @ManyToOne
    @JoinColumn(name="cd_forma_farmaceutica")
    private FormaFarmaceutica formaFarmaceutica;
    
    @Column(name="nr_dose_diaria_definida")
    private Long numeroDoseDiariaDefinida;
    
    @Column(name="tp_controle")
    private Long tipoControle;
    
    @ManyToOne
    @JoinColumn(name="cd_procedimento_vigente")
    private ProcedimentoVigente procedimentoVigente;
    
    @Column(name="sn_ativo")
    private Boolean ativo;
    
    @OneToMany(mappedBy = "medicamento", targetEntity = MedicamentoItemUnidade.class)
    private Collection<MedicamentoItemUnidade> medicamentosItensUnidade;

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

    public TipoReceituario getTipoReceituario()
    {
        return tipoReceituario;
    }

    public void setTipoReceituario(TipoReceituario tipoReceituario)
    {
        this.tipoReceituario = tipoReceituario;
    }

    public Boolean getPlacebo()
    {
        return placebo;
    }

    public void setPlacebo(Boolean placebo)
    {
        this.placebo = placebo;
    }

    public Boolean getTermolabel()
    {
        return termolabel;
    }

    public void setTermolabel(Boolean termolabel)
    {
        this.termolabel = termolabel;
    }

    public Long getTipoEspecificoSexo()
    {
        return tipoEspecificoSexo;
    }

    public void setTipoEspecificoSexo(Long tipoEspecificoSexo)
    {
        this.tipoEspecificoSexo = tipoEspecificoSexo;
    }

    public Long getTipoPadronizado()
    {
        return tipoPadronizado;
    }

    public void setTipoPadronizado(Long tipoPadronizado)
    {
        this.tipoPadronizado = tipoPadronizado;
    }

    public Boolean getDispensaRetorno()
    {
        return dispensaRetorno;
    }

    public void setDispensaRetorno(Boolean dispensaRetorno)
    {
        this.dispensaRetorno = dispensaRetorno;
    }

    public Boolean getFracionaEmbalagem()
    {
        return fracionaEmbalagem;
    }

    public void setFracionaEmbalagem(Boolean fracionaEmbalagem)
    {
        this.fracionaEmbalagem = fracionaEmbalagem;
    }

    public FormaFarmaceutica getFormaFarmaceutica()
    {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(FormaFarmaceutica formaFarmaceutica)
    {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public Long getNumeroDoseDiariaDefinida()
    {
        return numeroDoseDiariaDefinida;
    }

    public void setNumeroDoseDiariaDefinida(Long numeroDoseDiariaDefinida)
    {
        this.numeroDoseDiariaDefinida = numeroDoseDiariaDefinida;
    }

    public Long getTipoControle()
    {
        return tipoControle;
    }

    public void setTipoControle(Long tipoControle)
    {
        this.tipoControle = tipoControle;
    }

    public ProcedimentoVigente getProcedimentoVigente()
    {
        return procedimentoVigente;
    }

    public void setProcedimentoVigente(ProcedimentoVigente procedimentoVigente)
    {
        this.procedimentoVigente = procedimentoVigente;
    }

    public Boolean getAtivo()
    {
        return ativo;
    }

    public void setAtivo(Boolean ativo)
    {
        this.ativo = ativo;
    }

    public Collection<MedicamentoItemUnidade> getMedicamentosItensUnidade()
    {
        return medicamentosItensUnidade;
    }

    public void setMedicamentosItensUnidade(Collection<MedicamentoItemUnidade> medicamentosItensUnidade)
    {
        this.medicamentosItensUnidade = medicamentosItensUnidade;
    }

   
}
