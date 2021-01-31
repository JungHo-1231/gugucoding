package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestBookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Slf4j
@RequiredArgsConstructor
public class GuestbookController {


    private final GuestbookService guestbookService;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }


    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list............." + pageRequestDTO);
        PageResultDTO<GuestBookDTO, Guestbook> list = guestbookService.getList(pageRequestDTO);
        model.addAttribute("result", guestbookService.getList(pageRequestDTO));

        return "/guestbook/list";
    }


    @GetMapping("/register")
    public String register(){
        log.info("register get...");

        return "/guestbook/register";
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..." + dto);

        //새로 추가된 엔티티의 번호
        Long gno = guestbookService.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }


    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){

        log.info("gno :" + gno);
        guestbookService.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read","/modify"})
    public String read(Long gno, PageRequestDTO pageRequestDTO, Model model){
        log.info("gno : "+ gno);
        GuestBookDTO dto = guestbookService.read(gno);
        model.addAttribute("dto", dto);
        model.addAttribute("requestDTO", pageRequestDTO);
        return "/guestbook/read";
    }

    @PostMapping("/modify")
    public String modify(GuestBookDTO dto, PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){

        log.info("post modify........................................");
        log.info("dto: " + dto);

        guestbookService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }

}
