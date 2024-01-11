package org.choongang.board.service.config;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardConfig;
import org.choongang.board.entities.Board;
import org.choongang.board.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardRepository boardRepository;

    public void save(RequestBoardConfig form){
        String bid = form.getBid();

        Board board = boardRepository.findById(bid).orElseGet(Board::new);
    }
}
