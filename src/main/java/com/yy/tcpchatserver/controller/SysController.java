package com.yy.tcpchatserver.controller;

import com.yy.tcpchatserver.common.R;
import com.yy.tcpchatserver.filter.SensitiveFilter;
import com.yy.tcpchatserver.pojo.FeedBack;
import com.yy.tcpchatserver.pojo.SensitiveMessage;
import com.yy.tcpchatserver.pojo.User;
import com.yy.tcpchatserver.pojo.vo.FeedBackResultVo;
import com.yy.tcpchatserver.pojo.vo.SensitiveMessageResultVo;
import com.yy.tcpchatserver.pojo.vo.SystemUserResponseVo;
import com.yy.tcpchatserver.pojo.vo.UpdateUserInfoRequestVo;
import com.yy.tcpchatserver.service.OnlineUserService;
import com.yy.tcpchatserver.service.SysService;
import com.yy.tcpchatserver.service.UserService;
import com.yy.tcpchatserver.utils.FastDFSUtil;
import com.yy.tcpchatserver.utils.SystemUtil;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.management.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys")
public class SysController {

    @Resource
    private SysService sysService;

    @Value("${fastdfs.nginx.host}")
    private String nginxHost;

    @Resource
    private SensitiveFilter sensitiveFilter;

    @Resource
    private UserService userService;

    @Resource
    private OnlineUserService onlineUserService;


    /**
     * 获取注册时的头像列表
     */
    @GetMapping("/getFaceImages")
    @ResponseBody
    public R getFaceImages() {
        //String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/face";
        //System.out.println(path);
        ArrayList<String> files = new ArrayList<>();
        //File file = new File(path);
        /*for (File item : Objects.requireNonNull(file.listFiles())) {
            files.add(item.getName());
        }*/
        for (int i = 1; i <= 22; i++) {
            files.add("face" + i + ".jpg");
        }
        files.add("ronaldo1.jpg");
        return R.ok().data("files", files);
    }

    /**
     * 获取系统用户
     */
    @GetMapping("/getSysUsers")
    @ResponseBody
    public R getSysUsers() {
        List<SystemUserResponseVo> sysUsers = sysService.getSysUsers();
        // System.out.println("系统用户有：" + sysUsers);
        return R.ok().data("sysUsers", sysUsers);
    }

    /**
     * 上传文件
     */
    @PostMapping("/uploadFile")
    @ResponseBody
    public R uploadFile(MultipartFile file) throws IOException, MyException {
        //根据扩展名来设置消息类型：emoji/text/img/file/sys/whiteboard/video/audio
        String filePartName = FastDFSUtil.uploadFile(file);
        String filePath = nginxHost + filePartName;
        // System.out.println("在服务器的文件名为：" + filePartName);
        return R.ok().data("filePath", filePath);
    }

    //获取文件的真实地址，主要用于防盗链
    /*@GetMapping("/getRealFilePath")
    public R getRealFilePath(String fileId) throws UnsupportedEncodingException, NoSuchAlgorithmException, MyException {
        String fileUrl = FastDFSUtil.getToken(fileId);
        System.out.println("返回的真实路径为：" + fileUrl);
        return R.ok().data("realFilePath", fileUrl);
    }*/

    /**
     * 提供文件下载
     */
    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam("fileId") String fileId,
                             @RequestParam("fileName") String fileName,
                             HttpServletResponse resp) {
        try {
            byte[] bytes = FastDFSUtil.downloadFile(fileId);
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            ServletOutputStream outputStream = resp.getOutputStream();
            IOUtils.write(bytes, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 搜索好友或加过的群聊列表
     */
    /*@GetMapping("/topSearch")
    @ResponseBody
    public R topSearch(String keyword) {

        return R.ok();
    }*/

    /**
     * 用户反馈
     */
    @PostMapping("/addFeedBack")
    @ResponseBody
    public R addFeedBack(@RequestBody FeedBack feedBack) {
        // System.out.println("反馈请求参数为：" + feedBack);
        sysService.addFeedBack(feedBack);
        return R.ok().message("感谢您的反馈！");
    }

    /**
     * 过滤发送的消息
     */
    @PostMapping("/filterMessage")
    @ResponseBody
    public R filterMessage(@RequestBody SensitiveMessage sensitiveMessage) {
        String[] res = sensitiveFilter.filter(sensitiveMessage.getMessage());
        String filterContent = "";
        if (res != null) {
            filterContent = res[0];
            if (res[1].equals("1")) {
                //判断出敏感词，插入到敏感词表中
                sysService.addSensitiveMessage(sensitiveMessage);
            }
        }
        return R.ok().data("message", filterContent);
    }

    /**
     * 获取系统cpu、内存使用率
     */
    @GetMapping("/sysSituation")
    @ResponseBody
    public R getSysInfo() {
        double cpuUsage = SystemUtil.getSystemCpuLoad();
        double memUsage = SystemUtil.getSystemMemLoad();
        return R.ok().data("cpuUsage", cpuUsage).data("memUsage", memUsage);
    }

    /**
     * 获取所有用户信息
     */
    @GetMapping("/getAllUser")
    @ResponseBody
    public R getAllUser() {
        List<User> userList = userService.getUserList();
        return R.ok().data("userList", userList);
    }
    /**
     * 根据uuid删除用户
     */
    @GetMapping("/deleteUser")
    @ResponseBody
    public R deleteUser(String uid) {
        userService.deleteUser(uid);
        return R.ok();
    }

    /**
     * 更新用户的重要信息
     */
    @PostMapping("/updateUserInfo")
    public R updateUserInfo(@RequestBody UpdateUserInfoRequestVo requestVo) {
        System.out.println("更新用户信息的请求参数为：" + requestVo);
        Map<String, Object> resMap = userService.updateUserInfo(requestVo);
        if (resMap.size() > 0) return R.error().code((Integer) resMap.get("code")).message((String) resMap.get("msg"));
        else return R.ok().message("修改成功");
    }

    /**
     * 根据注册时间获取用户
     */
    @GetMapping("/getUsersBySignUpTime")
    @ResponseBody
    public R getUsersBySignUpTime(String lt, String rt) {
        List<User> userList = userService.getUsersBySignUpTime(lt, rt);
        return R.ok().data("userList", userList);
    }

    /**
     * 获取在线用户个数
     */
    @GetMapping("/countOnlineUser")
    @ResponseBody
    public R getOnlineUserNums() {
        int onlineUserCount = onlineUserService.countOnlineUser();
        return R.ok().data("onlineUserCount", onlineUserCount);
    }

    /**
     * 更改用户状态
     */
    @GetMapping("/changeUserStatus")
    @ResponseBody
    public R changeUserStatus(String uid, Integer status) {
        userService.changeUserStatus(uid, status);
        return R.ok();
    }

    /**
     * 获取所有敏感消息列表
     */
    @GetMapping("/getSensitiveMessageList")
    @ResponseBody
    public R getSensitiveMessageList() {
        List<SensitiveMessageResultVo> sensitiveMessageList = sysService.getSensitiveMessageList();
        return R.ok().data("sensitiveMessageList", sensitiveMessageList);
    }

    /**
     * 获取所有反馈记录列表
     */
    @GetMapping("/getFeedbackList")
    @ResponseBody
    public R getFeedbackList() {
        List<FeedBackResultVo> feedbackList = sysService.getFeedbackList();
        return R.ok().data("feedbackList", feedbackList);
    }
}