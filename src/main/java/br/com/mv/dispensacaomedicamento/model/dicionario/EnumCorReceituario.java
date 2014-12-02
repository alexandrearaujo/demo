package br.com.mv.dispensacaomedicamento.model.dicionario;

public enum EnumCorReceituario
{

    AMARELA(1L, "AMARELA"),
    AZUL(2L, "AZUL"),
    BRANCA(3L, "BRANCA");
    
    private Long id; 
    private String descricao;
    
    private EnumCorReceituario(Long id, String descricao)
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
