package br.com.mv.dispensacaomedicamento.exception;

import org.hibernate.HibernateException;

public class TipoFrequenciaException extends HibernateException
{
    
private static final long serialVersionUID = 1L;
    
    public static final String EXCEPTION_PK = "Chave primária violada";
    public static final String EXCEPTION_UNIQUE_CONSTRAINT = "Já existe um Tipo de frequencia cadastrada com esta descrição";

    public static final String CNT_TIPO_FREQUENCIA_1_UK = "Já existe um Tipo de frequencia cadastrada com esta descrição";
    
    public TipoFrequenciaException (String msg)
    {
        super(msg);
    }


}
