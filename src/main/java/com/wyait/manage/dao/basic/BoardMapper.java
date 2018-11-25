package com.wyait.manage.dao.basic;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.wyait.manage.entity.basic.BoardDTO;
import com.wyait.manage.entity.basic.BoardSearchDTO;
import com.wyait.manage.entity.basic.BoardVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.basic.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    Board selectByPrimaryKey(Integer id);

    /**
     * 分页查询工位数据
     * @return
     */
    List<BoardDTO> getBoards(@Param("boardSearchDTO") BoardSearchDTO boardSearchDTO);

    /**
     * 删除/恢复工位
     * @param id
     * @param isDel
     * @return
     */
    int setDelBoard(@Param("id") Integer id, @Param("isDel") Integer isDel);

    /**
     * 查询工位信息
     * @param id
     * @return
     */
    BoardVO getBoard(Integer id);

    /**
     * 根据工位号获取工位数据
     * @param boardCode
     * @return
     */
    Board findBoardByBoardCode(String boardCode);

    int insert(Board board);

    int updateByPrimaryKeySelective(Board board);
}

