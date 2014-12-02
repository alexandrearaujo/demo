package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;
import java.util.Date;

public class SolicitacaoMedicamentoDTO implements Serializable
{
    
    private static final long serialVersionUID = -7214225222620580535L;
    
    private Long id;
    private String nomePaciente;
    private String codigoReceita;
    private Long idEstabelecimentoSolicitante;
    private String estabelecimentoSolicitante;
    private Long idSetorEstabSolicitante;
    private String setorEstabSolicitante;
    private String farmaciaAtendimento;
    private Long idSituacaoSolicitacao;
    private String descSituacaoSolcitacao;
    private String strDataSolicitacao;
    private String strDataConsulta;
    private Date dataSolicitacao;
    private Date dataConsulta;
    private Date dataInicioSolicitacao;
    private Date datafimSolicitacao;
    private Date dataInicioConsulta;
    private Date datafimConsulta;
    private Boolean apac;
    private Long versao;
    
    private Long ROWNUM_;
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getNomePaciente()
    {
        return nomePaciente;
    }
    public void setNomePaciente(String nomePaciente)
    {
        this.nomePaciente = nomePaciente;
    }
    public String getCodigoReceita()
    {
        return codigoReceita;
    }
    public void setCodigoReceita(String codigoReceita)
    {
        this.codigoReceita = codigoReceita;
    }
    public Long getIdEstabelecimentoSolicitante()
    {
        return idEstabelecimentoSolicitante;
    }
    public void setIdEstabelecimentoSolicitante(Long idEstabelecimentoSolicitante)
    {
        this.idEstabelecimentoSolicitante = idEstabelecimentoSolicitante;
    }
    public String getEstabelecimentoSolicitante()
    {
        return estabelecimentoSolicitante;
    }
    public void setEstabelecimentoSolicitante(String estabelecimentoSolicitante)
    {
        this.estabelecimentoSolicitante = estabelecimentoSolicitante;
    }
    public Long getIdSetorEstabSolicitante()
    {
        return idSetorEstabSolicitante;
    }
    public void setIdSetorEstabSolicitante(Long idSetorEstabSolicitante)
    {
        this.idSetorEstabSolicitante = idSetorEstabSolicitante;
    }
    public String getSetorEstabSolicitante()
    {
        return setorEstabSolicitante;
    }
    public void setSetorEstabSolicitante(String setorEstabSolicitante)
    {
        this.setorEstabSolicitante = setorEstabSolicitante;
    }
    public String getFarmaciaAtendimento()
    {
        return farmaciaAtendimento;
    }
    public void setFarmaciaAtendimento(String farmaciaAtendimento)
    {
        this.farmaciaAtendimento = farmaciaAtendimento;
    }
    public Long getIdSituacaoSolicitacao()
    {
        return idSituacaoSolicitacao;
    }
    public void setIdSituacaoSolicitacao(Long idSituacaoSolicitacao)
    {
        this.idSituacaoSolicitacao = idSituacaoSolicitacao;
    }
    public String getDescSituacaoSolcitacao()
    {
        return descSituacaoSolcitacao;
    }
    public void setDescSituacaoSolcitacao(String descSituacaoSolcitacao)
    {
        this.descSituacaoSolcitacao = descSituacaoSolcitacao;
    }

    public Date getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(Date dataSolicitacao)
    {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Date getDataConsulta() {
		return dataConsulta;
	}
	public void setDataConsulta(Date dataConsulta)
    {
        this.dataConsulta = dataConsulta;
    }

    public Date getDataInicioSolicitacao() {
		return dataInicioSolicitacao;
	}
	public void setDataInicioSolicitacao(Date dataInicioSolicitacao)
    {
        this.dataInicioSolicitacao = dataInicioSolicitacao;
    }

    public Date getDatafimSolicitacao() {
		return datafimSolicitacao;
	}
	public void setDatafimSolicitacao(Date datafimSolicitacao)
    {
        this.datafimSolicitacao = datafimSolicitacao;
    }

    public Date getDataInicioConsulta() {
		return dataInicioConsulta;
	}
	public void setDataInicioConsulta(Date dataInicioConsulta)
    {
        this.dataInicioConsulta = dataInicioConsulta;
    }

    public Date getDatafimConsulta() {
		return datafimConsulta;
	}
	public void setDatafimConsulta(Date datafimConsulta)
    {
        this.datafimConsulta = datafimConsulta;
    }
    public String getStrDataSolicitacao()
    {
        return strDataSolicitacao;
    }
    public void setStrDataSolicitacao(String strDataSolicitacao)
    {
        this.strDataSolicitacao = strDataSolicitacao;
    }
    public String getStrDataConsulta()
    {
        return strDataConsulta;
    }
    public void setStrDataConsulta(String strDataConsulta)
    {
        this.strDataConsulta = strDataConsulta;
    }
    public Boolean getApac()
    {
        return apac;
    }
    public void setApac(Boolean apac)
    {
        this.apac = apac;
    }
    public Long getVersao()
    {
        return versao;
    }
    public void setVersao(Long versao)
    {
        this.versao = versao;
    }
    public Long getROWNUM_()
    {
        return ROWNUM_;
    }
    public void setROWNUM_(Long rOWNUM_)
    {
        ROWNUM_ = rOWNUM_;
    }
}
