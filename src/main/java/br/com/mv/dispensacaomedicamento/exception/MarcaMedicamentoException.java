package br.com.mv.dispensacaomedicamento.exception;

import org.hibernate.HibernateException;

public class MarcaMedicamentoException extends HibernateException
{

    public static final String MARCA_MEDICAMENTO_JA_CADASTRADA = "Já existe uma marca de medicamento cadastrada com o nome informado.";
    public static final String MARCA_MEDICAMENTO_VINCULADA = "Marca de medicamento não pode ser excluída pois já exta sendo utilizada.";
    
    
    public MarcaMedicamentoException(String msg)
    {
        super(msg);
    }
}
