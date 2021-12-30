package edu.ncst.controller;

import edu.ncst.entity.Audit;
import edu.ncst.entity.Tasks;
import edu.ncst.service.AuditService;
import edu.ncst.service.TaskService;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

//controller三个功能 接受用户的输入，调用业务层完成相应的功能,返回页面相应数据
@Controller
@RequestMapping("/audit")
public class AuditController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private AuditService auditService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index(Model model){
        List<Audit> audits = auditService.findAll();//调用业务层
        model.addAttribute("audits",audits);//传递数据
        return "audit_index";//返回网页
    }

    @RequestMapping(value = "/audit",method = RequestMethod.GET)
    public String audit(Integer task_id ,Model model){
        Audit audit = new Audit();
        audit.setTask_id(task_id);
        model.addAttribute("entity",audit);
        return "audit";//返回网页
    }
    //进入审核任务界面
    @RequestMapping(value = "/audit",method = RequestMethod.POST)
    public String auditsave(Audit audit ,Model model){
        auditService.save(audit);
        Tasks tasks=taskService.findone(audit.getTask_id());
        model.addAttribute("entity",tasks);
        return "task_edit_managers";//返回网页
    }

}
