package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.dispensacaomedicamento.model.HistoricoSolicitacaoMedicamento;
import br.com.mv.dispensacaomedicamento.model.SolicitacaoMedicamento;
import br.com.mv.dispensacaomedicamento.repository.HistoricoSolicitacaoMedicamentoRepository;

public class HistoricoSolicitacaoMedicamentoBusiness
{

    @Autowired
    private HistoricoSolicitacaoMedicamentoRepository historicoSolicitacaoMedicamentoRepository;
	
    public HistoricoSolicitacaoMedicamento salvarHistoricoSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento) throws DataIntegrityViolationException,
            InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        HistoricoSolicitacaoMedicamento historicoSolicitacaoMedicamento = new HistoricoSolicitacaoMedicamento();
        
        historicoSolicitacaoMedicamento.setSolicitacaoMedicamento(solicitacaoMedicamento);
        historicoSolicitacaoMedicamento.setSituacaoSolicitacaoMedicamento(solicitacaoMedicamento.getSituacaoSolicitacaoMedicamento());
        historicoSolicitacaoMedicamento.setUsuario(solicitacaoMedicamento.getUsuario());
        historicoSolicitacaoMedicamento.setDataCadastro(new Date());
        
        return salvar(historicoSolicitacaoMedicamento);
    }

    public HistoricoSolicitacaoMedicamento salvar(HistoricoSolicitacaoMedicamento historicoSolicitacaoMedicamento) throws DataIntegrityViolationException,
            InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return historicoSolicitacaoMedicamentoRepository.save(historicoSolicitacaoMedicamento);
    }

    public void atualizar() throws DataIntegrityViolationException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        
    }

    public void excluir(HistoricoSolicitacaoMedicamento historicoSolicitacaoMedicamento) throws DataIntegrityViolationException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        historicoSolicitacaoMedicamento.setSolicitacaoMedicamento(null);
        historicoSolicitacaoMedicamentoRepository.delete(historicoSolicitacaoMedicamento);
    }

    public Collection<HistoricoSolicitacaoMedicamento> buscarHistorioSolicitacaoMedicamentoPorSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento) 
    {
        return historicoSolicitacaoMedicamentoRepository.buscarHistorioSolicitacaoMedicamentoPorSolicitacaoMedicamento(solicitacaoMedicamento);
    }
    
    
    
}
