package controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import adressBook.Actions;
import adressBook.Contact;
import adressBook.Relations;
import adressBook.RelationsInfo;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MainController{
	
	@RequestMapping(value="/myAddressBook", method=RequestMethod.GET)
	public ModelAndView home(Model model){
		
		model.addAttribute("allContacts", Actions.fetchAllContacts());
		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/myAddressBook/addNew", method=RequestMethod.GET)
	public ModelAndView formNewContact(Model model){
		
		if(!model.containsAttribute("contact")){	
			model.addAttribute("contact", new Contact());
		}
		
		return new ModelAndView("newContact");
	}
	
	@RequestMapping(value="/myAddressBook/addNew", method=RequestMethod.POST)
	public RedirectView addNewContact(@Valid Contact contact, BindingResult result, RedirectAttributes redirect, Model model){

		if(result.hasErrors()){
			redirect.addFlashAttribute("contact", contact);	
			return new RedirectView("redirect:/myAddressBook/addNew");
		}
		
		Actions.save(contact);	
		model.addAttribute("allContacts", Actions.fetchAllContacts());
		return new RedirectView("/myAddressBook");
		
	}
	
	@RequestMapping(value = "/myAddressBook/delete", method = RequestMethod.POST)
	public ModelAndView deleteContact(@RequestParam("id") int id, Model model) {
		
			Contact contact = Actions.findContactById(id);
			Actions.delete(contact);		
		    Actions.deleteRelations(contact.getId());
			model.addAttribute("allContacts", Actions.fetchAllContacts());
			return new ModelAndView("redirect:/myAddressBook");
		
	}
	
	@RequestMapping(value="/myAddressBook/update/{id}", method=RequestMethod.GET)
	public ModelAndView formUpdateContact(@PathVariable int id, Model model, RedirectAttributes redirect){
		
		if(!model.containsAttribute("contact")){
			model.addAttribute("contact", Actions.findContactById(id));
		}
		
		return new ModelAndView("update");
	}
	
	@RequestMapping(value="/myAddressBook/update/{id}", method=RequestMethod.POST)
	public RedirectView updateContact(@Valid Contact contact, BindingResult result, RedirectAttributes redirect, Model model){
		
		if(result.hasErrors() && contact!= null){
			redirect.addFlashAttribute("contact", contact);
			return new RedirectView("redirect:/myAddressBook/update/" + contact.getId());
		}	
		
		Actions.update(contact);	
		model.addAttribute("allContacts", Actions.fetchAllContacts());
		return new RedirectView("/myAddressBook");
		
	}
	
	@RequestMapping(value = "/myAddressBook", method = RequestMethod.POST)
	public ModelAndView detailsOfContact(@RequestParam("id") int id, Model model) {
			
		 	model.addAttribute("contact", Actions.findContactById(id));  
		 	List<RelationsInfo> list = new ArrayList<RelationsInfo>();
		 	for(Relations rel : Actions.findAllRelations(id)){
		 		if(rel.getId1() == id){
		 			Contact c = Actions.findContactById(rel.getId2());
		 			RelationsInfo relInf = new RelationsInfo(c.getFirstName(), c.getLastName(), rel.getRelationSecondToFirst());
		 			list.add(relInf);
		 			model.addAttribute("relInf", relInf);

		 		}
		 		else if(rel.getId2() == id){
		 			Contact c = Actions.findContactById(rel.getId1());
		 			RelationsInfo relInf = new RelationsInfo(c.getFirstName(), c.getLastName(), rel.getRelationFirstToSecond());
		 			list.add(relInf); 
		 			model.addAttribute("relInf", relInf);

		 		}
		 	}
		 	
		 	model.addAttribute("relationsInfo", list);
			return new ModelAndView("details");
		
	}

	
	
	
}