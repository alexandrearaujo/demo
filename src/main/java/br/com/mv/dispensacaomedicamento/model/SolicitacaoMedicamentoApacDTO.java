package br.com.mv.dispensacaomedicamento.model;

import java.util.Date;

public class SolicitacaoMedicamentoApacDTO extends SolicitacaoMedicamentoDTO
{

    private static final long serialVersionUID = 465860214422579724L;
    
    private Long numeroApac;
    private Date dataInicio;
    private Date dataFim;
    private String dataInicioStr;
    private String dataFimStr;
    private Long tipoHemofilia;
    private Long tipoInibidor;
    private Long idVinculoProfissional;
    private Long idProfissional;
    private String regConselhoProfissional;
    
    private Long idProfissionalExe;
    private String regConselhoProfExe;
    
    private Long numeroPeso;
    private Long numeroAltura;
    private boolean transplante;
    private Long quantidadeTransplante;
    private boolean gestante;
    
    private Long idProcCidPrincipal;

    private Long idProcePrincipal;
    private String descProcedimentoPrin;
    private Date dataCompetenciaProcePrin;
    private String dataCompetProcePrinSrc;
    
    public Long idCidPrincipal;
    public String codigoCidPrin;
    public String descricaoCidPrin;
    private String  cidApacPrincipal;
    
    private Long idProcCidSecundario;
    
    private Long idProceSecundario;
    private String descProcedimentoSecun;
    private Date dataCompetenciaProceSecun;
    private String dataCompetProceSecunSrc;
    private String  cidApacSecundario;
    
    public Long idCidSecundario;
    public String codigoCidSecun;
    public String descricaoCidSecun;
    
    private String observacao;
    private Date dataAutorizacao;
    private Date dataInicioTratamento;
    private Long  idSoliMedicamentoAnterior;
    
    private Long idEstabelecimento;
    private String nomeFantasiaEstab;
    private Long idCidadaoPaciente;
    private String nomeCidadao;
    private Date nascimentoCidadao;
    private String nascimentoCidadaoStr;
    private Long sexoCidadao;
    
    private boolean renovada;

    public Long getNumeroApac()
    {
        return numeroApac;
    }

    public void setNumeroApac(Long numeroApac)
    {
        this.numeroApac = numeroApac;
    }


    public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio)
    {
        this.dataInicio = dataInicio;
    }



    public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim)
    {
        this.dataFim = dataFim;
    }

    public Long getTipoHemofilia()
    {
        return tipoHemofilia;
    }

    public void setTipoHemofilia(Long tipoHemofilia)
    {
        this.tipoHemofilia = tipoHemofilia;
    }

    public Long getTipoInibidor()
    {
        return tipoInibidor;
    }

    public void setTipoInibidor(Long tipoInibidor)
    {
        this.tipoInibidor = tipoInibidor;
    }

    public Long getIdProfissional()
    {
        return idProfissional;
    }

    public void setIdProfissional(Long idProfissional)
    {
        this.idProfissional = idProfissional;
    }

    public String getRegConselhoProfissional()
    {
        return regConselhoProfissional;
    }

    public void setRegConselhoProfissional(String regConselhoProfissional)
    {
        this.regConselhoProfissional = regConselhoProfissional;
    }

    public Long getIdProfissionalExe()
    {
        return idProfissionalExe;
    }

    public void setIdProfissionalExe(Long idProfissionalExe)
    {
        this.idProfissionalExe = idProfissionalExe;
    }

    public String getRegConselhoProfExe()
    {
        return regConselhoProfExe;
    }

    public void setRegConselhoProfExe(String regConselhoProfExe)
    {
        this.regConselhoProfExe = regConselhoProfExe;
    }

    public Long getNumeroPeso()
    {
        return numeroPeso;
    }

    public void setNumeroPeso(Long numeroPeso)
    {
        this.numeroPeso = numeroPeso;
    }

    public Long getNumeroAltura()
    {
        return numeroAltura;
    }

    public void setNumeroAltura(Long numeroAltura)
    {
        this.numeroAltura = numeroAltura;
    }

    public boolean isTransplante()
    {
        return transplante;
    }

    public void setTransplante(boolean transplante)
    {
        this.transplante = transplante;
    }

    public Long getQuantidadeTransplante()
    {
        return quantidadeTransplante;
    }

    public void setQuantidadeTransplante(Long quantidadeTransplante)
    {
        this.quantidadeTransplante = quantidadeTransplante;
    }

    public boolean isGestante()
    {
        return gestante;
    }

    public void setGestante(boolean gestante)
    {
        this.gestante = gestante;
    }

    public Long getIdProcCidPrincipal()
    {
        return idProcCidPrincipal;
    }

    public void setIdProcCidPrincipal(Long idProcCidPrincipal)
    {
        this.idProcCidPrincipal = idProcCidPrincipal;
    }

    public String getDescProcedimentoPrin()
    {
        return descProcedimentoPrin;
    }

    public void setDescProcedimentoPrin(String descProcedimentoPrin)
    {
        this.descProcedimentoPrin = descProcedimentoPrin;
    }

    public Long getIdCidPrincipal()
    {
        return idCidPrincipal;
    }

    public void setIdCidPrincipal(Long idCidPrincipal)
    {
        this.idCidPrincipal = idCidPrincipal;
    }

    public String getCodigoCidPrin()
    {
        return codigoCidPrin;
    }

    public void setCodigoCidPrin(String codigoCidPrin)
    {
        this.codigoCidPrin = codigoCidPrin;
    }

    public String getDescricaoCidPrin()
    {
        return descricaoCidPrin;
    }

    public void setDescricaoCidPrin(String descricaoCidPrin)
    {
        this.descricaoCidPrin = descricaoCidPrin;
    }

    public Long getIdProcCidSecundario()
    {
        return idProcCidSecundario;
    }

    public void setIdProcCidSecundario(Long idProcCidSecundario)
    {
        this.idProcCidSecundario = idProcCidSecundario;
    }

    public String getDescProcedimentoSecun()
    {
        return descProcedimentoSecun;
    }

    public void setDescProcedimentoSecun(String descProcedimentoSecun)
    {
        this.descProcedimentoSecun = descProcedimentoSecun;
    }

    public Long getIdCidSecundario()
    {
        return idCidSecundario;
    }

    public void setIdCidSecundario(Long idCidSecundario)
    {
        this.idCidSecundario = idCidSecundario;
    }

    public String getCodigoCidSecun()
    {
        return codigoCidSecun;
    }

    public void setCodigoCidSecun(String codigoCidSecun)
    {
        this.codigoCidSecun = codigoCidSecun;
    }

    public String getDescricaoCidSecun()
    {
        return descricaoCidSecun;
    }

    public void setDescricaoCidSecun(String descricaoCidSecun)
    {
        this.descricaoCidSecun = descricaoCidSecun;
    }

    public String getObservacao()
    {
        return observacao;
    }

    public void setObservacao(String observacao)
    {
        this.observacao = observacao;
    }

    public Date getDataAutorizacao()
    {
        return dataAutorizacao;
    }

    public void setDataAutorizacao(Date dataAutorizacao)
    {
        this.dataAutorizacao = dataAutorizacao;
    }


    public Date getDataInicioTratamento() {
		return dataInicioTratamento;
	}

	public void setCidApacPrincipal(String cidApacPrincipal) {
		this.cidApacPrincipal = cidApacPrincipal;
	}

	public void setDataInicioTratamento(Date dataInicioTratamento)
    {
        this.dataInicioTratamento = dataInicioTratamento;
    }

    public Long getIdSoliMedicamentoAnterior()
    {
        return idSoliMedicamentoAnterior;
    }

    public void setIdSoliMedicamentoAnterior(Long idSoliMedicamentoAnterior)
    {
        this.idSoliMedicamentoAnterior = idSoliMedicamentoAnterior;
    }

    public boolean isRenovada()
    {
        return renovada;
    }

    public void setRenovada(boolean renovada)
    {
        this.renovada = renovada;
    }

    public Long getIdProcePrincipal()
    {
        return idProcePrincipal;
    }

    public void setIdProcePrincipal(Long idProcePrincipal)
    {
        this.idProcePrincipal = idProcePrincipal;
    }



    public Date getDataCompetenciaProcePrin() {
		return dataCompetenciaProcePrin;
	}

	public void setDataCompetenciaProcePrin(Date dataCompetenciaProcePrin)
    {
        this.dataCompetenciaProcePrin = dataCompetenciaProcePrin;
    }

    public Long getIdProceSecundario()
    {
        return idProceSecundario;
    }

    public void setIdProceSecundario(Long idProceSecundario)
    {
        this.idProceSecundario = idProceSecundario;
    }



    public Date getDataCompetenciaProceSecun() {
		return dataCompetenciaProceSecun;
	}

	public void setDataCompetenciaProceSecun(Date dataCompetenciaProceSecun)
    {
        this.dataCompetenciaProceSecun = dataCompetenciaProceSecun;
    }
    
    public Long getIdEstabelecimento()
    {
        return idEstabelecimento;
    }

    public void setIdEstabelecimento(Long idEstabelecimento)
    {
        this.idEstabelecimento = idEstabelecimento;
    }

    public Long getIdCidadaoPaciente()
    {
        return idCidadaoPaciente;
    }

    public void setIdCidadaoPaciente(Long idCidadaoPaciente)
    {
        this.idCidadaoPaciente = idCidadaoPaciente;
    }
    
    public String getNomeCidadao()
    {
        return nomeCidadao;
    }

    public void setNomeCidadao(String nomeCidadao)
    {
        this.nomeCidadao = nomeCidadao;
    }

    public Date getNascimentoCidadao()
    {
        return nascimentoCidadao;
    }

    public void setNascimentoCidadao(Date nascimentoCidadao)
    {
        this.nascimentoCidadao = nascimentoCidadao;
    }

    public Long getSexoCidadao()
    {
        return sexoCidadao;
    }

    public void setSexoCidadao(Long sexoCidadao)
    {
        this.sexoCidadao = sexoCidadao;
    }

    public String getCidApacPrincipal()
    {
        return cidApacPrincipal;
    }

    public void setCidApac(String cidApacPrincipal)
    {
        this.cidApacPrincipal = cidApacPrincipal;
    }
    
    public String getCidApacSecundario()
    {
        return cidApacSecundario;
    }

    public void setCidApacSecundario(String cidApacSecundario)
    {
        this.cidApacSecundario = cidApacSecundario;
    }

    public String getDataInicioStr()
    {
        return dataInicioStr;
    }

    public void setDataInicioStr(String dataInicioStr)
    {
        this.dataInicioStr = dataInicioStr;
        
        Date data = new Date();
        try
        {
            data = Util.formataData(this.dataInicioStr);
        }
        catch (Exception e)
        {
            data = null;
        } 
        
        setDataInicio(data);
    }

    public String getDataFimStr()
    {
        return dataFimStr;
    }

    public void setDataFimStr(String dataFimStr)
    {
        this.dataFimStr = dataFimStr;
        
        Date data = new Date();
        try
        {
            data = Util.formataData(this.dataFimStr);
        }
        catch (Exception e)
        {
            data = null;
        } 
        
        setDataFim(data);
    }

    public String getNascimentoCidadaoStr()
    {
        return nascimentoCidadaoStr;
    }

    public void setNascimentoCidadaoStr(String nascimentoCidadaoStr)
    {
        this.nascimentoCidadaoStr = nascimentoCidadaoStr;
        
        Date data = new Date();
        try
        {
            data = Util.formataData(this.nascimentoCidadaoStr);
        }
        catch (Exception e)
        {
            data = null;
        } 
        
        setDataFim(data);
    }

    public String getDataCompetProcePrinSrc()
    {
        return dataCompetProcePrinSrc;
    }

    public void setDataCompetProcePrinSrc(String dataCompetProcePrinSrc)
    {
        this.dataCompetProcePrinSrc = dataCompetProcePrinSrc;
        
        Date data = new Date();
        try
        {
            data = Util.formataData(this.dataCompetProcePrinSrc);
        }
        catch (Exception e)
        {
            data = null;
        } 
        
        setDataCompetenciaProcePrin(data);
    }

    public String getNomeFantasiaEstab()
    {
        return nomeFantasiaEstab;
    }

    public void setNomeFantasiaEstab(String nomeFantasiaEstab)
    {
        this.nomeFantasiaEstab = nomeFantasiaEstab;
    }

    public String getDataCompetProceSecunSrc()
    {
        return dataCompetProceSecunSrc;
    }

    public void setDataCompetProceSecunSrc(String dataCompetProceSecunSrc)
    {
        this.dataCompetProceSecunSrc = dataCompetProceSecunSrc;
        
        Date data = new Date();
        try
        {
            data = Util.formataData(this.dataCompetProceSecunSrc);
        }
        catch (Exception e)
        {
            data = null;
        } 
        
        setDataCompetenciaProceSecun(data);
    }

    public Long getIdVinculoProfissional()
    {
        return idVinculoProfissional;
    }

    public void setIdVinculoProfissional(Long idVinculoProfissional)
    {
        this.idVinculoProfissional = idVinculoProfissional;
    }
}
