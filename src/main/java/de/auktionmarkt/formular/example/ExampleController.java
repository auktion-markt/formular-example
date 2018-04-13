package de.auktionmarkt.formular.example;

import de.auktionmarkt.formular.specification.FormSpecification;
import de.auktionmarkt.formular.specification.mapper.FormMapper;
import de.auktionmarkt.formular.state.FormState;
import de.auktionmarkt.formular.state.StateFactory;
import de.auktionmarkt.formular.state.applicator.FormStateApplicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class ExampleController {

    private final ExampleEntityRepository entityRepository;
    private final FormMapper formMapper;
    private final StateFactory stateFactory;
    private final FormStateApplicator applicator;

    @Autowired
    public ExampleController(ExampleEntityRepository entityRepository,
                             FormMapper formMapper,
                             StateFactory stateFactory,
                             FormStateApplicator applicator) {
        this.entityRepository = entityRepository;
        this.formMapper = formMapper;
        this.stateFactory = stateFactory;
        this.applicator = applicator;
    }

    @RequestMapping("/")
    public String listAction(Model model, Pageable pageable) {
        model.addAttribute("data", entityRepository.findAll(pageable));
        return "list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createAction(Model model) {
        FormSpecification formSpecification = formMapper.mapFormSpecification(ExampleEntity.class, "post", "/create");
        model.addAttribute("form_specification", formSpecification);
        model.addAttribute("form_state", stateFactory.createEmptyState(formSpecification));
        return "edit";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String doCreateAction(Model model, @Valid ExampleEntity entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FormSpecification formSpecification = formMapper.mapFormSpecification(ExampleEntity.class, "post", "/create");
            model.addAttribute("form_specification", formSpecification);
            model.addAttribute("form_state", stateFactory.createStateFromBindingResult(formSpecification, bindingResult));
            return "edit";
        }
        entity = entityRepository.save(entity);
        return "redirect:/" + entity.getId();
    }

    @RequestMapping(value = "/{entity}", method = RequestMethod.GET)
    public String editAction(Model model, @PathVariable ExampleEntity entity) {
        FormSpecification formSpecification = formMapper.mapFormSpecification(ExampleEntity.class, "post", "/" + entity.getId());
        model.addAttribute("entity", entity);
        model.addAttribute("form_specification", formSpecification);
        model.addAttribute("form_state", stateFactory.createStateFromModel(formSpecification, entity));
        return "edit";
    }

    @RequestMapping(value = "/{entity}", method = RequestMethod.POST)
    public String doEditAction(Model model, @PathVariable ExampleEntity entity, @Valid ExampleEntity formEntity, BindingResult bindingResult) {
        FormSpecification formSpecification = formMapper.mapFormSpecification(ExampleEntity.class, "post", "/" + entity.getId());
        FormState formState = stateFactory.createStateFromBindingResult(formSpecification, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("entity", entity);
            model.addAttribute("form_specification", formSpecification);
            model.addAttribute("form_state", formState);
            return "edit";
        }
        applicator.applyTo(formState, entity);
        entity = entityRepository.save(entity);
        return "redirect:/" + entity.getId();
    }

    @RequestMapping(value = "/{entity}/delete", method = RequestMethod.GET)
    public String deleteAction(@PathVariable ExampleEntity entity, Model model) {
        model.addAttribute("entity", entity);
        return "delete";
    }

    @RequestMapping(value = "/{entity}/delete", method = RequestMethod.POST)
    public String doDeleteAction(@PathVariable ExampleEntity entity) {
        entityRepository.delete(entity);
        return "redirect:/";
    }
}
