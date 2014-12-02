package br.com.mv.dispensacaomedicamento.model;

import java.io.Serializable;

public class ProcedimentoCidDTO implements Serializable
{
   
    private static final long serialVersionUID = -7783227876021052827L;
    
    private Long codigoProcedimentoCid;
    private Long codigoCid;
    private String cid;
    private String descricaoCid;

    public Long getCodigoProcedimentoCid()
    {
        return codigoProcedimentoCid;
    }

    public void setCodigoProcedimentoCid(Long codigoProcedimentoCid)
    {
        this.codigoProcedimentoCid = codigoProcedimentoCid;
    }

    public Long getCodigoCid()
    {
        return codigoCid;
    }

    public void setCodigoCid(Long codigoCid)
    {
        this.codigoCid = codigoCid;
    }

    public String getCid()
    {
        return cid;
    }

    public void setCid(String cid)
    {
        this.cid = cid;
    }

    public String getDescricaoCid()
    {
        return descricaoCid;
    }

    public void setDescricaoCid(String descricaoCid)
    {
        this.descricaoCid = descricaoCid;
    }
       
}
