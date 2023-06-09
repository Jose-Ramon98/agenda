package pe.todotic.agendavirtual0522.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.todotic.agendavirtual0522.model.Contacto;
import pe.todotic.agendavirtual0522.repository.ContactoRepository;

@Controller
public class ContactoController {

	
	
	@Autowired
	private ContactoRepository contactorepository;
	
	
	@GetMapping
	String index(Model model) {
		contactorepository.findAll();
		List<Contacto> contactos = contactorepository.findAll();
		model.addAttribute("contactos", contactos);
		return "index";
		
	}
	@GetMapping("/nuevo")
	String nuevo(Model model) { 
		model.addAttribute("contacto", new Contacto());
	return "nuevo";
	
	}
	@PostMapping("/nuevo")
	String crear(@Validated Contacto contacto,BindingResult bindingResult, RedirectAttributes ra, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("contacto", contacto);
			return "nuevo";
		}
		contacto.setFechaRegistro(LocalDateTime.now());
		contactorepository.save(contacto);
		ra.addFlashAttribute("msgExito", "El contacto se ha creado correctamente.");		
		return "redirect:/";
	}
	@GetMapping("/{id}/editar")
	String editar(@PathVariable Integer id, Model model) { 
	Contacto contacto = contactorepository. getReferenceById(id);
	model.addAttribute("contacto", contacto);
	return "nuevo";
		
	}	
	@PostMapping("/{id}/editar")
	String actualizar(@PathVariable Integer id,
			@Validated Contacto contacto,
			BindingResult bindingResult,
			RedirectAttributes ra,
			Model model) 
	{
		
		if(bindingResult.hasErrors()) {
				model.addAttribute("contacto", contacto);
				return "nuevo";
			}
		
		Contacto contactoFromDB = contactorepository. getReferenceById(id);
		contactoFromDB.setNombre(contacto.getNombre());	
		contactoFromDB.setCelular(contacto.getCelular());
		contactoFromDB.setEmail(contacto.getEmail());
		contactoFromDB.setFechaNacimiento(contacto.getFechaNacimiento());
		
	
		contactorepository.save(contactoFromDB);
		ra.addFlashAttribute("msgExito", "El contacto se ha actualizado correctamente.");		
		return "redirect:/";
	}
	
	
	
	
	
	
	
}