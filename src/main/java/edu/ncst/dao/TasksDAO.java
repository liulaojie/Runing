package edu.ncst.dao;


import edu.ncst.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequestMapping
public interface TasksDAO extends JpaRepository<Tasks,Integer>
{
    //发布者发布的任务
    @Query(value = "" +
            "select distinct task_table.* " +
            "from task_table " +
            "where publisher_id=?" ,
            nativeQuery = true)
    public List<Tasks> findTasksByPublisher_id(Integer publisher_id);
    //跑腿小哥接受的任务
    @Query(value = "" +
            "select distinct task_table.* " +
            " from task_table" +
            " where task_id in (" +
            "select distinct task_id" +
            " from deal_table " +
            "where runner_id=?)" ,
            nativeQuery = true)
    public List<Tasks> findTasksByRunner_id(Integer runner_id);
    //公开的任务
    @Query(value = "" +
            "select distinct task_table.* " +
            "from task_table " +
            "where task_table.taskPublicState=1"  ,
            nativeQuery = true)
    public List<Tasks> findPublicTasks();
    //拒绝公开的任务
    @Query(value = "" +
            "select distinct task_table.* " +
            "from task_table " +
            "where task_table.taskPublicState=2" ,
            nativeQuery = true)
    public List<Tasks> findUnpublishedTasks();

    //未审核的任务
    @Query(value = "" +
            "select  distinct  task_table.* " +
            " from task_table " +
            " where  task_id  not in " +
            "(select task_id from audit_table ) " ,
            nativeQuery = true)
    public List<Tasks> findUnauditTasks();

    //任务接受修改任务状态
    @Query(value = "" +
            "update task_table" +
            " set taskState=2 " +
            "where task_id=?"  ,
            nativeQuery = true)
    public void alterTaskStateByaccept (Integer task_id);
    //任务审核修改任务发布状态
    @Query(value = "" +
            "update task_table" +
            " set taskPublicState=? " +
            "where task_id=?"  ,
            nativeQuery = true)
    public void alterTaskPublicState ( Integer taskPublicState,Integer task_id);
}
