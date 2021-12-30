package edu.ncst.controller;

import edu.ncst.entity.Tasks;
import edu.ncst.service.DealService;
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
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    //注入TaskService

    @Autowired
    private DealService dealService;
    //传给taskhall_publisher显示发布者的发布的任务
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index( Integer publisher_id,Model model){
        List<Tasks> tasks = taskService.findTasksByPublisher_id(publisher_id);//调用业务层
        model.addAttribute("tasks",tasks);//传递数据
        model.addAttribute("publisher_id",publisher_id);
        return "taskhall_publisher";//返回网页
    }
    //传给taskhall_runner显示所有公开的任务
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public String allindex(Model model){
        List<Tasks> tasks = taskService.findPublicTasks();//调用业务层
        model.addAttribute("tasks",tasks);//传递数据
        return "taskhall_runner";//返回网页
    }
    //传给runnertask显示跑腿小哥的接受的任务
    @RequestMapping(value = "/runner",method = RequestMethod.GET)
    public String runnerindex(Integer runner_id,Model model){
        List<Tasks> tasks = taskService.findTasksByRunner_id(runner_id);//调用业务层
        model.addAttribute("tasks",tasks);//传递数据
        model.addAttribute("runner_id",runner_id);
        return "runnertask";//返回网页
    }
    //传给publisher_edit显示跑腿小哥完成的任务
    @RequestMapping(value = "/finish",method = RequestMethod.GET)
    public String finish(Integer task_id,Model model){
        Tasks tasks = taskService.findone(task_id) ;
        String fm="";
        return "taskhall_publisher";//返回网页
    }
    //传给task_edit显示任务编辑模板给发布者来发布任务
    @RequestMapping(value = "/edit" ,method = RequestMethod.GET)
    public  String edit( Integer publisher_id, Model model)
    {
        Tasks task = new Tasks();
        task.setPublisher_id(publisher_id);
        task.setTaskState(1);
        model.addAttribute("entity",task);//传递数据
        return "task_edit";
    }
    //传给taskhall_publisher显示发布者发布任务后所有发布者发布的任务
    @RequestMapping(value = "/edit" ,method = RequestMethod.POST)
    public  String editSave(Tasks task, Model model)
    {
        taskService.save(task);
        List<Tasks> tasks = taskService.findTasksByPublisher_id(task.getPublisher_id());//调用业务层
        model.addAttribute("tasks",tasks);//传递数据
        return "taskhall_publisher";
    }
    //保存管理者审核结果，再返回待审核任务界面
    @RequestMapping(value = "/manager" ,method = RequestMethod.POST)
    public  String managerEditSave(Tasks task, Model model)
    {
        taskService.save(task);
        List<Tasks> tasks = taskService.findUnauditTasks();//调用业务层
        model.addAttribute("tasks",tasks);//传递数据
        return "taskhall_manager";
    }
    //传给taskhall_manager显示未审核的任务
    @RequestMapping(value = "/audit" ,method = RequestMethod.GET)
    public  String audit( Model model)
    {
        List<Tasks> tasks = taskService.findUnauditTasks();//调用业务层
        model.addAttribute("tasks",tasks);//传递数据
        return "taskhall_manager";
    }
    //删除任务
    @RequestMapping(value = "/delete" ,method = RequestMethod.GET)
    public  String delete( Integer task_id,Model model)
    {
        Integer publisher_id = taskService.findone(task_id).getPublisher_id();
        taskService.delete(task_id);
        List<Tasks> tasks = taskService.findTasksByPublisher_id(publisher_id);//调用业务层
        model.addAttribute("tasks",tasks);//传递数据
        return "taskhall_publisher";
    }
    //放弃任务
    @RequestMapping(value = "/abandon" ,method = RequestMethod.GET)
    public  String abandon( Integer task_id,Model model)
    {
        Integer runner_id = dealService.findRunner_idBytask_id(task_id);
        Integer deal_id = dealService.findDeal_idBytask_id(task_id);
        dealService.delete(deal_id);
        List<Tasks> tasks = taskService.findTasksByRunner_id(runner_id);//调用业务层
        model.addAttribute("tasks",tasks);//传递数据
        model.addAttribute("runner_id",runner_id);
        return "runnertask";//返回网页
    }
}
