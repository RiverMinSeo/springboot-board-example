package com.example.springboard.controller;

import com.example.springboard.data.DeleteForm;
import com.example.springboard.data.BoardSaveForm;
import com.example.springboard.data.ReplyAddForm;
import com.example.springboard.dto.BoardDto;
import com.example.springboard.data.BoardAddForm;
import com.example.springboard.dto.ReplyDto;
import com.example.springboard.service.BoardService;
import com.example.springboard.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController {
    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;



    @GetMapping("/")
    public String index(@RequestParam(value = "page_no", defaultValue = "1", required = false) int pageNo, Model model) {
        Long totalBoardCnt = boardService.getBoardCount();
        List<BoardDto> boardList = boardService.getBoardlist(pageNo);
        List<Integer> pageList = boardService.getPageList(pageNo, totalBoardCnt);
        model.addAttribute("boardList", boardList);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", BoardService.PAGE_POST_COUNT);
        model.addAttribute("pageList", pageList);
        return "page/index";
    }

    @GetMapping("/{id:[0-9]+}")
    public String postDetail(@PathVariable("id") String id, Model model) {
        BoardDto board = boardService.getPost(Long.parseLong(id));
        long replyCount = replyService.getReplyCountInBoard(board.getId());
        List<ReplyDto> replies = replyService.getReplyListByBoard(0L, Long.parseLong(id));
        model.addAttribute("board", board);
        model.addAttribute("replyCount", replyCount);
        ReplyAddForm replyAddForm = new ReplyAddForm();
        model.addAttribute("replyForm", replyAddForm);
        return "page/post";
    }

    @GetMapping("/{id:[0-9]+}/replies")
    public String repliesInPost(@PathVariable("id") String id, Model model) {
        List<ReplyDto> replies = replyService.getReplyListByBoard(0L, Long.parseLong(id));
        model.addAttribute("replies", replies);
       return "page/post :: #replies";
    }

    @GetMapping("/form")
    public String postForm(@ModelAttribute("board") BoardDto form) {
        return "page/form";
    }

    @GetMapping("/{id:[0-9]+}/edit")
    public String editForm(@PathVariable("id") String id, Model model){
        Long boardId = Long.parseLong(id);
        BoardDto board = boardService.getPost(Long.parseLong(id));
        board.setPassword(null);
        model.addAttribute("board", board);
        return "page/edit";
    }

    @PostMapping("/save")
    public String savePost(@Validated @ModelAttribute("board") BoardSaveForm form, BindingResult result, Model model) {
        BoardDto boardDto = boardService.getPost(form.getId());
        if(!form.getPassword().equals("") && !boardDto.getPassword().equals(form.getPassword())){
            result.addError(new FieldError("board", "password", "비밀번호가 맞지 않습니다."));
        }

        if(result.hasErrors()){
            return "page/edit";
        }

        BoardDto updateBoardDto = form.toDto();
        boardService.updatePost(updateBoardDto);

        return "redirect:/"+form.getId();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deletePost(
            @RequestBody DeleteForm form
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        BoardDto boardDto = boardService.getPost(form.getId());
        if(form.getPassword().equals("") || !boardDto.getPassword().equals(form.getPassword())){
            resultMap.put("success", false);
            resultMap.put("message", "비밀번호가 맞지 않습니다.");
        }else{
            resultMap.put("success", true);
            boardService.deletePost(form.getId());
        }
        return resultMap;
    }

    @PostMapping("/post")
    public String addPost(@Validated @ModelAttribute("board") BoardAddForm form, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "page/form";
        }
        Long insertedId = boardService.newPost(form.toDto());
        return "redirect:/";
    }

    @PostMapping("/{id:[0-9]+}/reply")
    public String addReply(@PathVariable("id") String id, @Validated @ModelAttribute("replyForm") ReplyAddForm form, BindingResult result, Model model) {
        Long boardId = Long.parseLong(id);
        if(result.hasErrors()){
            return "page/post :: #replyForm";
        }
        ReplyDto replyDto = form.toDto();
        replyDto.setTbBoardId(boardId);
        replyService.setReply(replyDto);
        model.addAttribute("replyForm", new ReplyAddForm());
        return "page/post :: #replyForm";
    }

    @PostMapping("/reply/delete")
    @ResponseBody
    public Map<String, Object> deleteReply(
            @RequestBody Map<String, String> params) {

        Map<String, Object> resultMap = new HashMap<>();

        Long replyId = Long.parseLong(params.get("id"));
        ReplyDto replyDto = replyService.getReplyById(replyId);

        if(!replyDto.getPassword().equals(params.get("password"))){
            resultMap.put("success", false);
            resultMap.put("message", "비밀번호가 맞지 않습니다.");
        }else{
            replyService.deleteReplyById(replyId);
            resultMap.put("success", true);
        }

        return resultMap;
    }

    @PostMapping("/reply/save")
    @ResponseBody
    public Map<String, Object> updateReply(@RequestBody Map<String, String> params) {

        Map<String, Object> resultMap = new HashMap<>();
        Long replyId = Long.parseLong(params.get("id"));
        ReplyDto replyDto = replyService.getReplyById(replyId);

        String inputPwd = params.get("password");
        String inputContent = params.get("content");

        if(!replyDto.getPassword().equals(inputPwd)){
            resultMap.put("success", false);
            resultMap.put("error-password", "비밀번호가 맞지 않습니다.");
        }else if(inputContent.isEmpty()){
            resultMap.put("success", false);
            resultMap.put("error-content", "내용을 입력해 주세요.");
        }else {
            replyDto.setContent(inputContent);
            replyService.updateReply(replyDto);
            resultMap.put("success", true);
        }

        return resultMap;
    }

}
