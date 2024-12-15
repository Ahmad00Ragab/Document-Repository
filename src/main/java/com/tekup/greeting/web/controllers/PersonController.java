package com.tekup.greeting.web.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.tekup.greeting.bussiness.services.AgeGroupService;
import com.tekup.greeting.bussiness.services.PersonService;
import com.tekup.greeting.dao.entities.Person;
import com.tekup.greeting.web.models.requests.PersonForm;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/* Carte des opérations CRUD
 * 
 * Create -Get  /persons/create : Récuprer un formulaire d'ajout d'une nouvelle personnse
 *        -Post /persons/create : Ajouter une personne à la liste persons
 * 
 * Read   -Get   /persons    : Récupérer une liste de personnes
 * 
 * Read with filter : /persons/filter?sortByAge=<asc|desc>
 *     GET  -> Trier la liste des personnes par âge (ascendant ou descendant)
 * 
 * Update -Get  /persons/{id}/edit : Récupérer un formulaire de mise à jour d'une personne
 *        -Post /persons/{id}/edit : Mettre à jour une personne dans la liste persons
 * 
 * Delete -Post /persons/{id}/delete : Supprimer une personne de la liste persons
 * 
 */

@Controller
public class PersonController {

    /*
     * / private static List<Person> persons = new ArrayList<Person>();
     * private static Long idCount = 0L;
     */
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";

    /*
     * static {
     * persons.add(new Person(++idCount, "demo1", (short) 20, "men.png"));
     * persons.add(new Person(++idCount, "demo2", (short) 30, "women.png"));
     * persons.add(new Person(++idCount, "demo3", (short) 40, null));
     * persons.add(new Person(++idCount, "demo4", (short) 50, "men.png"));
     * }
     */
    private final PersonService personService;
    private final AgeGroupService ageGroupService;

    public PersonController(PersonService personService,AgeGroupService ageGroupService) {
        this.personService = personService;
        this.ageGroupService=ageGroupService;
    }

    @RequestMapping("/persons")
    /*
     * public String getAllPerson(Model model) {
     * // model.addAttribute("persons",
     * this.personService.getPersonsByName("demo1"));
     * model.addAttribute("persons", this.personService.getAllPerson());
     * // model.addAttribute("persons", persons);
     * return "person-list";
     * }
     */
    public String getAllPersons(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int pageSize,
            Model model) {
        // PageRequest est une implémentation de l'interface Pageable
        // page : numéro de la page (commence à 0).
        // size : nombre d'éléments par page.
        Page<Person> personPage = this.personService.getAllPersonPagination(PageRequest.of(page, pageSize));
        // Page<>est une interface qui fournit toutes les métadonnées d'une page comme:
        // liste des éléments de la page actuelle (getContent()).
        // Le nombre total de pages (getTotalPages()).
        // Le nombre total d'éléments (getTotalElements()).
        // Le numéro de la page actuelle (getNumber()).
        model.addAttribute("persons", personPage.getContent());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personPage.getTotalPages());

        return "person-list";
    }

    @RequestMapping("/persons/filter")
    /*
     * public String getPersonsSorted(@RequestParam(required = false, defaultValue
     * ="asc") String sortByAge,
     * Model model) {
     * model.addAttribute("persons",this.personService.getPersonSortedByAge(
     * sortByAge));
     * model.addAttribute("sortByAge", sortByAge);
     * return "person-list";
     * }
     */
    public String getPersonSorted(@RequestParam(required = false, defaultValue = "asc") String sortByAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int pageSize,
            Model model) {
        Page<Person> personPage = this.personService.getPersonSortedByAgePagination(sortByAge,
                PageRequest.of(page, pageSize));
        model.addAttribute("persons", personPage.getContent());
        model.addAttribute("sortByAge", sortByAge);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personPage.getTotalPages());
        return "person-list";
    }

    @RequestMapping("/persons/create")
    public String showAddPersonForm(Model model) {
        model.addAttribute("personForm", new PersonForm());
        model.addAttribute("ageGroups", this.ageGroupService.getAllAgeGroups());
        return "add-person";
    }

    @RequestMapping(path = "/persons/create", method = RequestMethod.POST)
    public String addPerson(@Valid @ModelAttribute PersonForm personForm,
            BindingResult bindingResult,
            Model model,
            @RequestParam MultipartFile file) {
        if (bindingResult.hasErrors()) {
            // model.addAttribute("error", "invalid input");
            return "add-person";
        }
        if (!file.isEmpty()) {
            StringBuilder fileName = new StringBuilder();
            fileName.append(file.getOriginalFilename());
            Path newFilePath = Paths.get(uploadDirectory, fileName.toString());

            try {
                Files.write(newFilePath, file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.personService
                    .addPerson(new Person(null, personForm.getName(), personForm.getAge(), fileName.toString()));
            // persons.add(new Person(++idCount, personForm.getName(), personForm.getAge(),
            // fileName.toString()));

        } else {
            // persons.add(new Person(++idCount, personForm.getName(), personForm.getAge(),
            // null));
            this.personService.addPerson(new Person(null, personForm.getName(), personForm.getAge(), null));
        }
        // persons.add(new Person(++idCount, personForm.getName(), personForm.getAge(),
        // null));
        return "redirect:/persons";
    }

    @RequestMapping("/persons/{id}/edit")
    public String showEditPersonForm(@PathVariable Long id, Model model) {
        /*
         * for (Person person : persons) {
         * if (person.getId() == id) {
         * model.addAttribute("personForm", new PersonForm(person.getName(),
         * person.getAge(), person.getPhoto()));
         * model.addAttribute("id", id);
         * return "edit-person";
         * }
         * }
         */
        Person personUpdate = this.personService.getPersonById(id);
        model.addAttribute("personForm",
                new PersonForm(personUpdate.getName(), personUpdate.getAge(), personUpdate.getPhoto(),personUpdate.getAgeGroup()));
        model.addAttribute("id", id);
        model.addAttribute("ageGroups", this.ageGroupService.getAllAgeGroups());
        return "edit-person";

        // return "redirect:/persons";
    }

    @RequestMapping(path = "/persons/{id}/edit", method = RequestMethod.POST)
    public String editPerson(@Valid @ModelAttribute PersonForm personForm,
            BindingResult bindingResult,
            @PathVariable Long id,
            Model model,
            @RequestParam MultipartFile file) {
        if (bindingResult.hasErrors()) {
            // model.addAttribute("error", "invalid input");
            return "edit-person";
        }
        /*
         * for(Person person:persons){
         * if(person.getId()==id){
         * person.setName(personForm.getName());
         * person.setAge(personForm.getAge());
         * person.setPhoto(person.getPhoto());
         * break;
         * }
         * }
         */
        /*
         * persons.stream()
         * .filter(person->person.getId()==id)
         * .findFirst()
         * .ifPresent(person-> {person.setName(personForm.getName());
         * person.setAge(personForm.getAge());
         * if (!file.isEmpty()) {
         * StringBuilder fileName = new StringBuilder();
         * Path newFilePath = Paths.get(uploadDirectory, file.getOriginalFilename());
         * fileName.append(file.getOriginalFilename());
         * try {
         * if(person.getPhoto()!=null){
         * Path filePath = Paths.get(uploadDirectory, person.getPhoto());
         * Files.deleteIfExists(filePath);
         * }
         * Files.write(newFilePath, file.getBytes());
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         * person.setPhoto(fileName.toString());
         * }}
         * );
         * return "redirect:/persons";
         */
        Person personUpdate = this.personService.getPersonById(id);
        personUpdate.setName(personForm.getName());
        personUpdate.setAge(personForm.getAge());
        personUpdate.setAgeGroup(personForm.getAgeGroup());
        if (!file.isEmpty()) {
            StringBuilder fileName = new StringBuilder();
            Path newFilePath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileName.append(file.getOriginalFilename());
            try {
                Files.write(newFilePath, file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            personUpdate.setPhoto(fileName.toString());
        }
        this.personService.updatePerson(personUpdate);
        return "redirect:/persons";

    }

    @RequestMapping(path = "/persons/{id}/delete", method = RequestMethod.POST)
    public String deletePerson(@PathVariable Long id) {
        /*
         * for(Person person:persons){
         * if(person.getId()==id){
         * persons.remove(person);
         * break;
         * }
         * }
         */
        /*
         * persons.stream()
         * .filter(person -> person.getId() == id)
         * .findFirst()
         * .ifPresent(person -> {
         * // Supprimer le fichier de photo si existe
         * if (person.getPhoto() != null) {
         * Path filePath = Paths.get(uploadDirectory, person.getPhoto());
         * try {
         * Files.deleteIfExists(filePath);
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         * }
         * // Supprimer la personne de la liste
         * persons.remove(person);
         * 
         * });
         */
        // persons.removeIf(person -> person.getId() == id);
        this.personService.deletePersonById(id);
        return "redirect:/persons";
    }

}