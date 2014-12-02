package br.com.mv.dispensacaomedicamento.model;

public class VinculoProfissionalDTO
{

    private Long idVinculoProfissional;
    private Long idProfissional;
    private Long idCidadao;
    private String nomeCidadao;
    private String cpfCidadao;
    private String cnsCidadao;
    private Long idOcupacao;
    private String descricaoOcupacao;
    private String registroConselho;
    private String siglaUfVinculoProfissional;
    
    
    public Long getIdVinculoProfissional()
    {
        return idVinculoProfissional;
    }
    public void setIdVinculoProfissional(Long idVinculoProfissional)
    {
        this.idVinculoProfissional = idVinculoProfissional;
    }
    public Long getIdProfissional()
    {
        return idProfissional;
    }
    public void setIdProfissional(Long idProfissional)
    {
        this.idProfissional = idProfissional;
    }
    public Long getIdCidadao()
    {
        return idCidadao;
    }
    public void setIdCidadao(Long idCidadao)
    {
        this.idCidadao = idCidadao;
    }
    public String getNomeCidadao()
    {
        return nomeCidadao;
    }
    public void setNomeCidadao(String nomeCidadao)
    {
        this.nomeCidadao = nomeCidadao;
    }
    public String getCpfCidadao()
    {
        return cpfCidadao;
    }
    public void setCpfCidadao(String cpfCidadao)
    {
        this.cpfCidadao = cpfCidadao;
    }
    public String getCnsCidadao()
    {
        return cnsCidadao;
    }
    public void setCnsCidadao(String cnsCidadao)
    {
        this.cnsCidadao = cnsCidadao;
    }
    public Long getIdOcupacao()
    {
        return idOcupacao;
    }
    public void setIdOcupacao(Long idOcupacao)
    {
        this.idOcupacao = idOcupacao;
    }
    public String getDescricaoOcupacao()
    {
        return descricaoOcupacao;
    }
    public void setDescricaoOcupacao(String descricaoOcupacao)
    {
        this.descricaoOcupacao = descricaoOcupacao;
    }
    public String getRegistroConselho()
    {
        return registroConselho;
    }
    public void setRegistroConselho(String registroConselho)
    {
        this.registroConselho = registroConselho;
    }
    public String getSiglaUfVinculoProfissional()
    {
        return siglaUfVinculoProfissional;
    }
    public void setSiglaUfVinculoProfissional(String siglaUfVinculoProfissional)
    {
        this.siglaUfVinculoProfissional = siglaUfVinculoProfissional;
    }
    
}
