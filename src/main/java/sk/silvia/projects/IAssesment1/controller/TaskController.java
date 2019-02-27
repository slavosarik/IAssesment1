package sk.silvia.projects.IAssesment1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.silvia.projects.IAssesment1.model.Task;
import sk.silvia.projects.IAssesment1.model.Manager;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

        @GetMapping("/schedule/new")
        public String newTask(Model model) {
            model.addAttribute("task", new Task());
            // return new empty task form
            return "new_task";
        }

        @PostMapping("/schedule/new")
        public String createTask(@ModelAttribute Task task) {
            if (task.getTaskCategory() == null) {
                task.setTaskCategory("Other");
            }
            taskRepository.save(task);
            return "/schedule";        // return homepage with task lists        // change to return "schedule"
        }

        @GetMapping("/schedule")
        public String loadTasks(Model model) {
            // load all tasks from database
            List<Task> taskList = taskRepository.findAll();
            // return homepage with tasks listed
            model.addAttribute("taskList", taskList);

            Manager man = new Manager(); List<String> categs = man.categoriesL;
            model.addAttribute("categs", categs);

            return "/schedule";
        }

        // napr: http://localhost:8080/schedule/25
        @GetMapping("/schedule/{id}")
        public String editTask(@PathVariable("id") Long id, Model model ) {

            Task t1  = new Task(); t1.setId(1L); t1.setName("AA"); t1.setDuration(1); t1.setTaskCategory("School");
            Task t2  = new Task(); t2.setId(35L); t2.setName("BB"); t2.setDuration(2); t2.setTaskCategory("Work");
            Task t3  = new Task(); t3.setId(45L); t3.setName("CC"); t3.setDuration(3); t2.setTaskCategory("Free time");
            taskRepository.save(t1); taskRepository.save(t2); taskRepository.save(t3);

            // load task from database - find task by id
            Task task = taskRepository.getOne(id);

            // fill form with task data
            model.addAttribute("task", task);

            // show form
            return "edit_task";
        }

        @PostMapping("/schedule/{id}")
        public String editTask(@ModelAttribute Task task ) {

            // fill form with task data
            taskRepository.save(task);

            // show form
            return "schedule";         //change to schedule
         }

        //get all data, one line
        @GetMapping("schedule/list")
        @ResponseBody
        public List<Task> getTaskList(){
            return taskRepository.findAll();
        }

}
