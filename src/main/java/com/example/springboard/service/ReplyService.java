package com.example.springboard.service;

import com.example.springboard.domain.Board;
import com.example.springboard.domain.Reply;
import com.example.springboard.dto.ReplyDto;
import com.example.springboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {
  private final ReplyRepository replyRepository;

  private ReplyDto convertEntityToDto(Reply reply){
    return ReplyDto.builder()
            .id(reply.getId())
            .author(reply.getAuthor())
            .content(reply.getContent())
            .password(reply.getPassword())
            .createdDate(reply.getCreatedDate())
            .modifiedDate(reply.getModifiedDate()).build();
  }

  public long getReplyCountInBoard(Long boardId) {
    return replyRepository.countByBoard(boardId);
  }

  public List<ReplyDto> getReplyListByBoard(Long replyId, Long boardId) {
    List<Reply> replyList = replyRepository.findByIdGreaterThanEqualAndTbBoardId(replyId, boardId);
    return replyList.stream().map((item)-> convertEntityToDto(item)).toList();
  }
}
