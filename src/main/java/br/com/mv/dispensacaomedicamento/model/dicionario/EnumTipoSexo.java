package br.com.mv.dispensacaomedicamento.model.dicionario;

public enum EnumTipoSexo
{
    NAO(0L, "NÃO"),
    MASCULINO(1L, "MASCULINO"),
    FEMININO(2L, "FEMININO");
    
    private Long id; 
    private String descricao;
    
    private EnumTipoSexo(Long id, String descricao)
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
