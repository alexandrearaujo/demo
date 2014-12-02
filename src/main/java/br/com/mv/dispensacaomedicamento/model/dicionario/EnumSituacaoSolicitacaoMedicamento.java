package br.com.mv.dispensacaomedicamento.model.dicionario;

public enum EnumSituacaoSolicitacaoMedicamento
{
    RECEITUARIO_ENTREGUE(1L, "RECEITUÁRIO ENTREGUE"),
    TRIAGEM(2L, "TRIAGEM"),
    EM_ATENDIMENTO(3L, "EM ATENDIMENTO"),
    FINALIZADO(4L, "FINALIZADO");
    
    private Long id; 
    private String descricao;
    
    private EnumSituacaoSolicitacaoMedicamento(Long id, String descricao)
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
