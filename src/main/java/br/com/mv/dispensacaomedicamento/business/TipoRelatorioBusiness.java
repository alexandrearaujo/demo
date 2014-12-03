package br.com.mv.dispensacaomedicamento.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.dispensacaomedicamento.repository.TipoRelatorioRepository;

@Service
@Transactional
public class TipoRelatorioBusiness
{

	@Autowired
    private TipoRelatorioRepository tipoRelatorioRepository;

}
