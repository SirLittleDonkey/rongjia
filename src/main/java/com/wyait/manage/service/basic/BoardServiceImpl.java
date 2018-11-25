package com.wyait.manage.service.basic;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.basic.BoardMapper;
import com.wyait.manage.entity.basic.BoardDTO;
import com.wyait.manage.entity.basic.BoardSearchDTO;
import com.wyait.manage.entity.basic.BoardVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.basic.Board;
import com.wyait.manage.utils.PageDataResult;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
    private static final Logger logger = LoggerFactory
            .getLogger(BoardServiceImpl.class);

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public PageDataResult getBoards(Integer page, Integer limit, BoardSearchDTO boardSearchDTO) {

        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<BoardDTO> boards = boardMapper.getBoards(boardSearchDTO);
        //获取分页查询后的数据
        PageInfo<BoardDTO> pageInfo = new PageInfo<>(boards);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(boards);
        return pdr;
    }

    @Override
    public String setDelBoard(Integer id, Integer isDel) {
        return this.boardMapper.setDelBoard(id, isDel) == 1 ? "ok": "删除失败，请您稍后再试";
    }

    @Override
    public BoardVO getBoard(Integer id) {
        return this.boardMapper.getBoard(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {
            RuntimeException.class, Exception.class })
    public String setBoard(Board board) {
        int boardId;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(board.getId() != null){
            Board existBoard = this.boardMapper.findBoardByBoardCode(board.getBoardCode());
            if(null != existBoard){
                if (String.valueOf(existBoard.getId()).equals(
                        String.valueOf(board.getId()))){
                    board.setInsertUid(user.getId());
                    this.boardMapper.updateByPrimaryKeySelective(board);
                } else {
                    return "该看板号已存在";
                }
            } else{
                board.setInsertUid(user.getId());
                this.boardMapper.updateByPrimaryKeySelective(board);
            }

        }else {
            //判断工位是否已经存在
            Board existBoard = this.boardMapper.findBoardByBoardCode(board.getBoardCode());
            if(null != existBoard){
                return "该看板号已存在";
            }
            board.setIsDel(false);
            board.setInsertUid(user.getId());
            this.boardMapper.insert(board);
        }
        return "ok";
    }
}

