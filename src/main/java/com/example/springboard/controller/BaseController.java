package com.example.springboard.controller;

import com.example.springboard.dto.BoardDto;
import com.example.springboard.dto.BoardFormDto;
import com.example.springboard.dto.ReplyDto;
import com.example.springboard.service.BoardService;
import com.example.springboard.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BaseController {
    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;

    @GetMapping("/")
    public String index(Model model) {
        List<BoardDto> boardList = boardService.getBoardlist(1);
        model.addAttribute("boardList", boardList);
        System.out.println(boardList.size());
        return "page/index";
    }

    @GetMapping("/{id:[\\d+]}")
    public String postDetail(@PathVariable("id") String id, Model model) {
        BoardDto board = boardService.getPost(Long.parseLong(id));
        long replyCount = replyService.getReplyCountInBoard(board.getId());
        List<ReplyDto> replies = replyService.getReplyListByBoard(0L, Long.parseLong(id));
        System.out.println(replies.size());
        model.addAttribute("board", board);
        model.addAttribute("replyCount", replyCount);
        return "page/post";
    }

    @GetMapping("/form")
    public String postForm(@ModelAttribute("board") BoardDto form) {
        return "page/form";
    }

    @PostMapping("/post")
    public String addPost(@Validated @ModelAttribute("board") BoardFormDto form, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "page/form";
        }
        Long insertedId = boardService.setPost(form.toDto());
        System.out.println(insertedId);
        return "redirect:/";
    }
}
