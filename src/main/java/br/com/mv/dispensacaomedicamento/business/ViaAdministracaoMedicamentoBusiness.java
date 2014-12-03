package br.com.mv.dispensacaomedicamento.business;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.dispensacaomedicamento.model.ViaAdministracaoMedicamento;
import br.com.mv.dispensacaomedicamento.repository.ViaAdministracaoMedicamentoRepository;

@Service
@Transactional
public class ViaAdministracaoMedicamentoBusiness
{

	@Autowired
    private ViaAdministracaoMedicamentoRepository viaAdministracaoMedicamentoRepository;
    
	public Collection<ViaAdministracaoMedicamento> listarViaAdministracaoMedicamentoAtivo() {
		return viaAdministracaoMedicamentoRepository.listarViaAdministracaoMedicamentoAtivo();
    }
}
