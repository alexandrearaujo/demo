package br.com.mv.dispensacaomedicamento.model.dicionario;

public enum EnumLocal
{
    
    NENHUM(0, "NENHUM"),
    ARMARIO(1, "ARMARIO"),
    GELADEIRA(2, "GELADEIRA");
    
    private int codigo; 
    private String descricao;
    
    private EnumLocal(int codigo, String descricao)
    {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getcodigo()
    {
        return codigo;
    }

    public void setcodigo(int codigo)
    {
        this.codigo = codigo;
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
