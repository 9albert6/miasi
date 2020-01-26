import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration().setJdbcUrl("jdbc:h2:mem:org.flowable;DB_CLOSE_DELAY=-1")
                                                                                   .setJdbcUsername("sa")
                                                                                   .setJdbcPassword("")
                                                                                   .setJdbcDriver("org.h2.Driver")
                                                                                   .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                                                 .addClasspathResource("miasi.bpmn20.xml")
                                                 .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                               .deploymentId(deployment.getId())
                                                               .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
        Scanner scanner= new Scanner(System.in);

        String mail = "michaj96";
        String destynacja = "Berlin, Niemcy";
        int iloscDni = 3;
        String data = "15.05.2020";

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("destynacja", destynacja);
        variables.put("iloscDni", iloscDni);
        variables.put("data", data);
        variables.put("mail", mail);
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("process", variables);


        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("pracownicy").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i=0; i<tasks.size(); i++) {
            System.out.println((i+1) + ") " + tasks.get(i).getName());
        }

        System.out.println("Which task would you like to complete?");
        int taskIndex = Integer.valueOf(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println("Klient chce wyjechac do " + processVariables.get("destynacja") + " na " +
                           processVariables.get("iloscDni") + " poczynajac od dnia " + processVariables.get("data") +
                           ". Podaj cene takiej wycieczki: ");

        float cena = Float.valueOf(scanner.nextLine());
//        variables = new HashMap<String, Object>();
        variables.put("cena", cena);
        taskService.complete(task.getId(), variables);
//
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities =
                historyService.createHistoricActivityInstanceQuery()
                              .processInstanceId(processInstance.getId())
                              .finished()
                              .orderByHistoricActivityInstanceEndTime().asc()
                              .list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " took "
                               + activity.getDurationInMillis() + " milliseconds");
        }
    }
}
