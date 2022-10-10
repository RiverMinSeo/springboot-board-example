package com.example.springboard.service;

import com.example.springboard.domain.Board;
import com.example.springboard.dto.BoardDto;
import com.example.springboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    public static final int PAGE_POST_COUNT = 10; // 한 페이지에 존재하는 게시글 수

    // Entity -> Dto로 변환
    private BoardDto convertEntityToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    public List<Integer> getPageList(Integer curPageNum, Long totalCount) {
        List<Integer> pageList = new ArrayList<>();
        Integer totalLastPageNum = (int)(Math.ceil((Double.valueOf(totalCount)/PAGE_POST_COUNT)));
        Integer pageGroup = (int)(Math.ceil((totalLastPageNum / BLOCK_PAGE_NUM_COUNT)));
        pageGroup = (pageGroup == 0) ? 1 : pageGroup;
        int pageTotal = Math.toIntExact(totalCount);
        int pageStart = 0;
        int pageEnd = (pageTotal <= BLOCK_PAGE_NUM_COUNT) ?  pageTotal : BLOCK_PAGE_NUM_COUNT;
        for(int i=0; i<pageGroup; i++){
            int startNum = (i * BLOCK_PAGE_NUM_COUNT) + 1;
            int endNum = startNum + (BLOCK_PAGE_NUM_COUNT - 1);
            if(curPageNum >=startNum && curPageNum <= endNum){
                pageStart = startNum;
                pageEnd = endNum;
                break;
            }
        }
        for(int i=pageStart; i<=pageEnd; i++){
            if(i > pageTotal) break;
            pageList.add(i);
        }
        return pageList;
    }

    public List<BoardDto> getBoardlist(Integer pageNum) {
        Pageable pagination = PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Board> page = boardRepository.findAll(pagination);

        List<Board> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(board));
        }
        return boardDtoList;
    }

    public BoardDto getPost(Long id) {
        // Optional : NPE(NullPointerException) 방지
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        BoardDto boardDTO = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .password(board.getPassword())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();

        return boardDTO;
    }

    @Transactional
    public Long newPost(BoardDto board){
        boardRepository.save(board.toEntity());
        return board.getId();
    }

    @Transactional
    public Long updatePost(BoardDto boardDto) {
        Board newEntity = boardDto.toEntity();
        Board board = boardRepository.findById(boardDto.getId()).orElseThrow(()-> new EntityNotFoundException());
        board.update(newEntity.getTitle(), newEntity.getContent(), newEntity.getAuthor());
        return board.getId();
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}
