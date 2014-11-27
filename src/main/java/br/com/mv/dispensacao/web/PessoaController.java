package br.com.mv.dispensacao.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.mv.dispensacao.model.Pessoa;
import br.com.mv.dispensacao.service.PessoaService;

@Controller
@SessionAttributes(types = Pessoa.class)
public class PessoaController {
	@Autowired
    private PessoaService pessoaService;
	
//	@Autowired
//	private PessoaRepository pessoaRepository;
	
	@RequestMapping(value = "/listPessoa", method = RequestMethod.GET)
    public String listPessoa(Model model) {
		model.addAttribute("pessoa", new Pessoa());
        return "listPessoa";
    }
	
	@RequestMapping(value = "/createPessoa", method = RequestMethod.GET)
    public String createPessoa(Model model) {
		model.addAttribute("pessoa", new Pessoa());
        return "createPessoa";
    }
	
	@RequestMapping(value="/createPessoa", method = RequestMethod.POST)
    public String salvar(@ModelAttribute("pessoa") @Valid Pessoa pessoa, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "createPessoa";
        }
        
        pessoaService.getPessoaRepository().save(pessoa);
        model.clear();
        return "redirect:/listPessoa";
    }
	
	
	@ModelAttribute("pessoas")
	public List<Pessoa> getPessoas() {
		return (List<Pessoa>) pessoaService.getPessoaRepository().findAll();
	}

	@RequestMapping("/createPessoa/editarPessoa/{indexOpcaoParametro}")
    public String editarPessoa(@ModelAttribute("pessoa") Pessoa pessoa,
    		final BindingResult bindingResult, @PathVariable int indexOpcaoParametro, Model model) {
//        createParametroForm.getParametro().getOpcoesParametros().remove(indexOpcaoParametro);
//        ModelAndView modelAndView = new ModelAndView("createParametro", "createParametroForm", createParametroForm);
		model.addAttribute("pessoa", pessoa);
        return "createPessoa";
    }
}
