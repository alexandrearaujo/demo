package br.com.mv.dispensacao.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class DispensacaoController {
	
	
	@RequestMapping("/")
    public String index() {
        return "index";
    }
	
	@ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("timestamp", new Date());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("exception");
        return mav;
    }
	
	@RequestMapping(value="/dispensar", method = RequestMethod.GET)
    public String dispensar(Model model) {
        return "dispensacao";
    }
	
	@RequestMapping(value="/chooser", method = RequestMethod.GET)
	public String chooser(Model model) {
		return "chooser";
	}

}
