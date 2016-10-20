package com.sivitsky.controller;

import com.sivitsky.domain.Section;
import com.sivitsky.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PropertySource("classpath:forum.properties")
public class SectionController {

    @Value("${model.items-per-page}")
    private int itemsPerPage;

    @Autowired
    private SectionService sectionService;

    public static Pageable updatePageable(final Pageable source, final int size) {
        return new PageRequest(source.getPageNumber(), size, source.getSort());
    }

    @RequestMapping("/sections")
    public String listSections(Model model, Pageable pageable) {
        Page<Section> sectionPage = sectionService.findAll(updatePageable(pageable, itemsPerPage));
        PageWrapper<Section> page = new PageWrapper<Section>(sectionPage, "/sections");
        model.addAttribute("sections", page.getContent());
        model.addAttribute("section", new Section());
        model.addAttribute("page", page);
        return "sections";
    }

    @RequestMapping(value = "/editSection", method = RequestMethod.POST)
    public String saveProduct(Section section) {
        sectionService.createUpdate(section);
        return "redirect:sections";
    }

    @RequestMapping("/sections/edit/{id}")
    public String editSection(@PathVariable Long id, Model model, Pageable pageable) {
        Page<Section> sectionPage = sectionService.findAll(updatePageable(pageable, itemsPerPage));
        PageWrapper<Section> page = new PageWrapper<Section>(sectionPage, "/sections");
        model.addAttribute("section", sectionService.findById(id));
        model.addAttribute("sections", page.getContent());
        model.addAttribute("page", page);
        return "sections";
    }

    @RequestMapping("/sections/delete/{id}")
    public String delete(@PathVariable Long id) {
        sectionService.deleteById(id);
        return "redirect:/sections";
    }

}
