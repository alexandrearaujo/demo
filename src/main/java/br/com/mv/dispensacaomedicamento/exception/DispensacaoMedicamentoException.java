package br.com.mv.dispensacaomedicamento.exception;

import org.hibernate.HibernateException;

@SuppressWarnings("serial")
public class DispensacaoMedicamentoException extends HibernateException
{
    
    
    /* PopUpCadastroProcedimentoAPAC Exception  */
    public static final String DATA_INICIAL_APAC_FORA_MES_COMPETENCIA = "A data inicial da solicitação de APAC deve ser dentro da competência vigente.";
    public static final String DATA_FINAL_APAC_SUPERIOR_90DIAS = "O período de validade da solicitação de APAC deve ser no máximo 90 dias.";
    public static final String QUANTIDA_MEDICAMENTO_MAXIMA_PERMITIDA = "Quantidade do medicamento acima do valor permitido";
    public static final String SOMATORIO_QUANTIDA_MEDICAMENTO_IGUAL_A_ZERO = "O Somatório das quantidades informadas para o medicamento não podem ser igual a 0";
    public static final String PACIENTE_CID_PRINCIPAL_MESMO_PERIODO_VALIDADE = "Já existe uma solicitação de APAC para o paciente e o cid principal no período de validade escolhido";
    public static final String PACIENTE_MEDICAMENTO_MESMO_PERIODO_VALIDADE = "Já existe uma solicitação de APAC para o paciente e o medicamento no período de validade escolhido";
    public static final String SOLICITACAO_MEDICAMENTO_MESMO_NUMERO_RECEITA_PACIENTE = "Já existe uma solicitação de medicamento cadastrada para o número da receita e paciente informado";
    public static final String FARMACIA_JA_CADASTRADA = "Já existe uma farmácia cadastrada com o nome informado";
    
    public DispensacaoMedicamentoException(String msg)
    {
        super(msg);
    }

}
