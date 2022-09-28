package com.example.springboard.controller;

import com.example.springboard.dto.ReplyDto;
import com.example.springboard.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ReplyController {
    @Autowired
    ReplyService replyService;
    @GetMapping("/board/{id:[\\d+]}/replies")
    public Map<String, Object> replies(@PathVariable("id") String id){
        long totReplyCnt = replyService.getReplyCountInBoard(Long.parseLong(id));
        List<ReplyDto> replies = replyService.getReplyListByBoard(0L, Long.parseLong(id));
        Map<String, Object> resBody = new HashMap<>();
        resBody.put("tot_reply_cnt", totReplyCnt);
        resBody.put("replies", replies);
        return resBody;
    }
}
