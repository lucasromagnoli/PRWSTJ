package br.com.lucasromagnoli.prwstj.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.lucasromagnoli.prwstj.domain.model.User;
import br.com.lucasromagnoli.prwstj.domain.repository.jpa.UsersJpaRepository;
import br.com.lucasromagnoli.prwstj.domain.service.UsersService;
import br.com.lucasromagnoli.prwstj.domain.support.PrwstjPropertiesSupport;
import br.com.lucasromagnoli.prwstj.domain.validation.UserValidator;
import br.com.lucasromagnoli.prwstj.web.constants.ControllerMapping;

@Controller
@RequestMapping(ControllerMapping.WEB_PATH_USERS_ROOT)
public class UsersController {

	@Autowired
	UserValidator userValidator;
	
	@Autowired
	PrwstjPropertiesSupport propertiesSupport;
	
	@Autowired
	UsersJpaRepository usersJpaRepository;
	
	@Autowired
	UsersService usersService;
	
	Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	@GetMapping(ControllerMapping.WEB_PATH_USERS_SIGNUP)
	public ModelAndView showSignUp(User user) {
		ModelAndView mv = new ModelAndView(ControllerMapping.VIEW_USERS_SIGNUP);

		return mv;
	}
	
	@PostMapping(ControllerMapping.WEB_PATH_USERS_SIGNUP)
	public ModelAndView doSignUp(User user, BindingResult result) {
		ModelAndView mv = new ModelAndView(ControllerMapping.VIEW_USERS_SIGNUP);
		logger.info(String.format("Tentava de cadastro, dados recebidos: %s", user.toString()));
		userValidator.validateSignUp(user, result);

		if(!result.hasErrors()) {
			logger.info("As informações foram validadas");
			try {
				usersService.save(user);
				logger.info(String.format("Usuario [%s] registrado no banco", user.getEmail()));
			} catch (Exception e) {
				logger.error(String.format("Erro ao cadastrar o usuario: %s", user.toString()));
			}
		}
		
		return mv;
	}

	@GetMapping(ControllerMapping.WEB_PATH_USERS_LIST)
	public ModelAndView showList(User user) {
		ModelAndView mv = new ModelAndView(ControllerMapping.VIEW_USERS_LIST);

		return mv;
	}
}
