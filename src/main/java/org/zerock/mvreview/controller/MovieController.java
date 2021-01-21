package org.zerock.mvreview.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mvreview.dto.MovieDto;
import org.zerock.mvreview.dto.PageRequestDto;
import org.zerock.mvreview.dto.PageResultDto;
import org.zerock.mvreview.service.MovieService;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/register")
    public void register() {}

    @PostMapping("/register")
    public String register(MovieDto movieDto, RedirectAttributes redirectAttributes) {
        log.info("movieDto: " + movieDto);

        Long mno = movieService.register(movieDto);

        redirectAttributes.addFlashAttribute("msg", mno);

        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) {
        log.info("pageRequestDto: " + pageRequestDto);

        PageResultDto<MovieDto, Object[]> result = movieService.getList(pageRequestDto);

        log.info("DTO: " + result);

        model.addAttribute("result", result);
    }
}
