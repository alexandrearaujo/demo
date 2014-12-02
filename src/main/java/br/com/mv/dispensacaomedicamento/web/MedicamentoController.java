package br.com.mv.dispensacaomedicamento.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.mv.dispensacaomedicamento.business.MedicamentoBusiness;

@Controller
public class MedicamentoController {
	
	@Autowired
	MedicamentoBusiness medicamentoBusiness;
	
	@ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("timestamp", new Date());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("exception");
        return mav;
    }	

	@RequestMapping(value="/listarMedicamento", method = RequestMethod.GET)
    public String listarMedicamento(Model model) {
		
		model.addAttribute("medicamentos", medicamentoBusiness.listarMedicamento());
        return "dataGrid";
    }

}
