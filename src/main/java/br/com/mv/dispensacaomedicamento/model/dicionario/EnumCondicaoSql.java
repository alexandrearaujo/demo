package br.com.mv.dispensacaomedicamento.model.dicionario;

public enum EnumCondicaoSql
{
    

    IGUAL(1L, "="),
    DIFERENTE(2L, "<>");
    
    private Long id; 
    private String descricao;
    
    private EnumCondicaoSql(Long id, String descricao)
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
