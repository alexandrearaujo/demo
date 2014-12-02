package br.com.mv.dispensacaomedicamento.model.dicionario;

public enum EnumCaracteristica
{
    
    UMIDADE(0, "UMIDADE"),
    LUZ_SOLAR(1, "LUZ SOLAR"),
    CALOR(2, "CALOR"),
    AREJADO(3, "AREJADO"),
    ALIMENTOS_OU_PROD_QUIMICO(4, "ALIMENTOS/PRODUTO QUIMICO"),
    PORTA_GELADEIRA_OU_CONGELADOR(5, "PORTA GELADEIRA/CONGELADOR");
    
    private int id; 
    private String descricao;
    
    private EnumCaracteristica(int id, String descricao)
    {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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
