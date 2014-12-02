package br.com.mv.dispensacaomedicamento.i18n;

public enum DispensacaoMedicamentoRBi
{

    MEDICAMENTO_EXISTENTE("medicamentoService.medicamentoExistente"),
    ERRO_INTERNO("erro.interno"),
    MEDICAMENTO_ITEM_UNIDADE_UTILIZADO("medicamentoService.medicamentoUtilizado"),
    MEDICAMENTO_ITEM_UNIDADE_NAO_PODE_INATIVAR("medicamentoService.medicamentoItemUnidadeNaoPodeSerInativado"),
    MEDICAMENTO_NAO_PODE_EXCLUIR("medicamentoService.medicamentoNaoPodeSerExcluido"),
    MEDICAMENTO_NAO_INATIVAR("medicamentoService.medicamentoNaoPodeSerInativado"),

    EXCLUIR_SOLICITACAO_MEDICAMENTO_APAC("SolicitacaoMedicamentoManagerImpl.mensagem.excluirSolicitacaoMedicamentoApac"),
    MOTIVO_DEVOLUCAO_MEDICAMENTO_EXISTENTE("motivoDevolucaoMedicamento.existente");
    
    

    private String key;

    private DispensacaoMedicamentoRBi(String key) {
    this.key = key;
    }

    @Override
    public String toString() {
    return this.key;
    }

    public static final String BASENAME = new String("dispensacao-medicamento");

}
