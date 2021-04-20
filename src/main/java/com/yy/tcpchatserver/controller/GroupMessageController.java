package com.yy.tcpchatserver.controller;

import com.yy.tcpchatserver.common.R;
import com.yy.tcpchatserver.pojo.vo.GroupHistoryResultVo;
import com.yy.tcpchatserver.pojo.vo.GroupMessageResultVo;
import com.yy.tcpchatserver.pojo.vo.HistoryMsgRequestVo;
import com.yy.tcpchatserver.service.GroupMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/groupMessage")
public class GroupMessageController {
    @Resource
    private GroupMessageService groupMessageService;

    /**
     * 获取最近的群消息
     */
    @GetMapping("/getRecentGroupMessages")
    public R getRecentGroupMessages(String roomId, Integer pageIndex, Integer pageSize) {
        List<GroupMessageResultVo> recentGroupMessages = groupMessageService.getRecentGroupMessages(roomId, pageIndex, pageSize);
        return R.ok().data("recentGroupMessages", recentGroupMessages);
    }

    /**
     * 获取群历史消息
     */
    @PostMapping("/historyMessages")
    public R getGroupHistoryMessages(@RequestBody HistoryMsgRequestVo historyMsgRequestVo) {
        GroupHistoryResultVo historyMessages = groupMessageService.getGroupHistoryMessages(historyMsgRequestVo);
        return R.ok().data("total", historyMessages.getCount()).data("msgList", historyMessages.getGroupMessages());
    }

    /**
     * 获取群最后一条消息
     */
    @GetMapping("/lastMessage")
    public R getGroupLastMessage(String roomId) {
        GroupMessageResultVo groupLastMessage = groupMessageService.getGroupLastMessage(roomId);
        return R.ok().data("groupLastMessage", groupLastMessage);
    }
}
