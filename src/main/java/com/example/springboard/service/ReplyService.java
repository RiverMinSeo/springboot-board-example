package com.example.springboard.service;

import com.example.springboard.domain.Reply;
import com.example.springboard.dto.ReplyDto;
import com.example.springboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

  public ReplyDto getReplyById(Long id) {
    Optional<Reply> replyWrapper = replyRepository.findById(id);
    Reply reply = replyWrapper.get();
    return convertEntityToDto(reply);
  }

  public List<ReplyDto> getReplyListByBoard(Long replyId, Long boardId) {
    List<Reply> replyList = replyRepository.findTop100ByIdGreaterThanEqualAndTbBoardId(replyId, boardId);
    return replyList.stream().map((item)-> convertEntityToDto(item)).toList();
  }
  @Transactional
  public Long setReply(ReplyDto replyDto) {
    Reply reply = replyDto.toEntity();
    replyRepository.save(reply);
    return replyDto.getId();
  }

  @Transactional
  public void deleteReplyById(Long id) {
    replyRepository.deleteById(id);
  }

  @Transactional
  public Long updateReply(ReplyDto replyDto){
    Reply updateReply = replyDto.toEntity();
    Reply reply = replyRepository.findById(replyDto.getId()).orElseThrow(()-> new EntityNotFoundException());
    reply.update(updateReply.getContent());
    return reply.getId();
  }
}
