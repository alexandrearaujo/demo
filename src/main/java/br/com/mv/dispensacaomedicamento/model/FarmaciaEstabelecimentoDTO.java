package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;

public class FarmaciaEstabelecimentoDTO implements Serializable
{

    private static final long serialVersionUID = 2302225210851222450L;
    
    private Long id;
    private Long idFarmacia;
    private Long idEstabelecimento;
    private String nomeFantasiaEstabelecimento;
    private Long crf;
    private String farmacia;
    private String estabelecimento;
    private String setor;
    private String nomeCidadao;
    
    
    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public Long getIdFarmacia()
    {
        return idFarmacia;
    }
    public void setIdFarmacia(Long idFarmacia)
    {
        this.idFarmacia = idFarmacia;
    }
    public Long getIdEstabelecimento()
    {
        return idEstabelecimento;
    }
    public void setIdEstabelecimento(Long idEstabelecimento)
    {
        this.idEstabelecimento = idEstabelecimento;
    }
    public String getNomeFantasiaEstabelecimento()
    {
        return nomeFantasiaEstabelecimento;
    }
    public void setNomeFantasiaEstabelecimento(String nomeFantasiaEstabelecimento)
    {
        this.nomeFantasiaEstabelecimento = nomeFantasiaEstabelecimento;
    }
    public Long getCrf()
    {
        return crf;
    }
    public void setCrf(Long crf)
    {
        this.crf = crf;
    }
    public String getFarmacia()
    {
        return farmacia;
    }
    public void setFarmacia(String farmacia)
    {
        this.farmacia = farmacia;
    }
    public String getEstabelecimento()
    {
        return estabelecimento;
    }
    public void setEstabelecimento(String estabelecimento)
    {
        this.estabelecimento = estabelecimento;
    }
    public String getSetor()
    {
        return setor;
    }
    public void setSetor(String setor)
    {
        this.setor = setor;
    }
    public String getNomeCidadao()
    {
        return nomeCidadao;
    }
    public void setNomeCidadao(String nomeCidadao)
    {
        this.nomeCidadao = nomeCidadao;
    }
    
    
}
