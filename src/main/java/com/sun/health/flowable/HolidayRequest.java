package com.sun.health.flowable;

import org.flowable.engine.*;
import org.flowable.engine.history.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by 华硕 on 2018-04-17.
 */
public class HolidayRequest {

    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                                            .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                                            .setJdbcUsername("sa")
                                            .setJdbcPassword("")
                                            .setJdbcDriver("org.h2.Driver")
                                            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = cfg.buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deploy = deploymentBuilder.addClasspathResource("holiday-request.bpmn").deploy();

        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploy.getId()).singleResult();
        System.out.println("Name: " + deployment.getName());


        Scanner scanner = new Scanner(System.in);
        System.out.println("你是谁?");
        String employee = scanner.nextLine();
        System.out.println("休假几天?");
        int nrOfHolidays = Integer.valueOf(scanner.nextLine());
        System.out.println("休假理由?");
        String description = scanner.nextLine();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);

        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("有" + tasks.size() + "个任务:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ")" + tasks.get(i).getName());
        }

        System.out.println("需要了解哪个任务?");
        int taskIndex = Integer.valueOf(scanner.nextLine());
//        Task task = taskService.createTaskQuery().taskId(tasks.get(taskIndex - 1).getId()).singleResult();
//
        Task task = tasks.get(taskIndex - 1);
//        Map<String, Object> processVariables = task.getProcessVariables();
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + "想要" + processVariables.get("nrOfHolidays") + "天休假，理由:" + processVariables.get("description"));

        System.out.println("是否同意(Y/N):");
        boolean approved = scanner.nextLine().toLowerCase().equals("y");
        Map<String, Object> taskCompleteParams = new HashMap<String, Object>();
        taskCompleteParams.put("approved", approved);
        taskService.complete(tasks.get(taskIndex - 1).getId(), taskCompleteParams);


        HistoryService historyService = processEngine.getHistoryService();
//        historyService.createHistoricTaskInstanceQuery().taskId(task.getId());
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println(historicProcessInstance.getProcessVariables());
        List<HistoricActivityInstance> activities = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getId())
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " took" + activity.getDurationInMillis() + "微秒");
            if(activity.getTaskId() != null) {
                List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().taskId(activity.getTaskId()).list();
                for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
                    System.out.println(historicVariableInstance.getValue() + ":" + historicVariableInstance.getVariableName());
                }
            }
            if(activity.getTaskId() != null) {
                List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().taskId(activity.getTaskId()).list();
                for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
                    Map<String, Object> historyProcessVariables = historicTaskInstance.getProcessVariables();
                    System.out.println(historyProcessVariables);
                    Map<String, Object> taskLocalVariables = historicTaskInstance.getTaskLocalVariables();
                    System.out.println(taskLocalVariables);
                }
            }
//            if(activity.getTaskId() != null) {
//                Map<String, Object> historyVariables = taskService.getVariables(activity.getTaskId());
//                System.out.println(historyVariables);
//            }
            if(activity.getTaskId() != null) {

            }
        }


    }

}
