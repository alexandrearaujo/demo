package br.com.mv.dispensacaomedicamento.model.dicionario;

public enum EnumPadronizacao
{
    PADRONIZADO(1L, "PADRONIZADO"),
    NAO_PADRONIZADO(2L, "NÃO PADRONIZADO"),
    EVENTUAL(3L, "EVENTUAL");
    
    private Long id; 
    private String descricao;
    
    private EnumPadronizacao(Long id, String descricao)
    {
        this.id = id;
        this.descricao = descricao;
    }

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
}
