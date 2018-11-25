package com.wyait.manage.service.basic;
import com.wyait.manage.entity.basic.BoardSearchDTO;
import com.wyait.manage.entity.basic.BoardVO;
import com.wyait.manage.pojo.basic.Board;
import com.wyait.manage.utils.PageDataResult;

public interface BoardService {
    public PageDataResult getBoards(Integer page, Integer limit, BoardSearchDTO boardSearchDTO);

    public String setDelBoard(Integer id, Integer isDel);

    public BoardVO getBoard(Integer id);

    public String setBoard(Board board);
}
