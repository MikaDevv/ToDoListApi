    package com.mika.tasks.service;

    import java.util.ArrayList;
    import java.util.Comparator;
    import java.util.Hashtable;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Service;

    import com.mika.tasks.entity.User;
    import com.mika.tasks.exception.TaskNotFoundException;
    import com.mika.tasks.exception.UnauthorizedException;
    import com.mika.tasks.dto.TaskRequestDTO;
    import com.mika.tasks.dto.TaskResponseDTO;
    import com.mika.tasks.dto.TaskUpdateDTO;
    import com.mika.tasks.entity.Task;
    import com.mika.tasks.entity.TaskStatus;
    import com.mika.tasks.repository.TaskRepository;
    import com.mika.tasks.repository.UserRepository;

    import lombok.RequiredArgsConstructor;

    @Service
    @RequiredArgsConstructor
    public class TaskService {

        private final UserRepository userRepository;
        private final TaskRepository taskRepository;
        
        public TaskResponseDTO createTask(TaskRequestDTO taskDTO){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> result = userRepository.findByEmail(email);
            User user = result.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            Task t = new Task();
            t.setTitle(taskDTO.title());
            t.setDescription(taskDTO.description());
            t.setUser(user);
            t.setTaskStatus(TaskStatus.PENDING);
            taskRepository.save(t);
            TaskResponseDTO taskDTO2 = new TaskResponseDTO(t.getId(), t.getTitle(), t.getDescription(), t.getTaskStatus(), t.getDate(), user.getName());
            return taskDTO2;   
        }
        public List<TaskResponseDTO> getTasksByUser(TaskStatus status){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> result = userRepository.findByEmail(email);
            User user = result.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            List<Task> tasks = taskRepository.findByUser(user);
            if(status != null) {
                tasks = tasks.stream()
                            .filter(t -> t.getTaskStatus().equals(status))
                            .collect(Collectors.toList());
            }
            return tasks.stream()
                        .sorted(Comparator.comparing(Task :: getDate).reversed())
                        .map(t -> new TaskResponseDTO(t.getId(), t.getTitle(), t.getDescription(), t.getTaskStatus(), t.getDate(), t.getUser().getName()))
                        .collect(Collectors.toList());

            /*Seria assim sem o stream: List<Task> tasks = taskRepository.findByUser(user);
            List<TaskResponseDTO> result2 = new ArrayList<>();
            for(Task t : tasks) {
                result2.add(new TaskResponseDTO(t.getId(), t.getTitle(), t.getDescription(), t.getTaskStatus(), t.getDate(), t.getUser().getName()));
                return result2;*/
        }
        public TaskResponseDTO updateTaskById(Long id, TaskUpdateDTO taskUpdateDTO){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Task result = taskRepository.findById(id)
                    .orElseThrow(() -> new TaskNotFoundException(id));
            if(result.getUser().getEmail().equals(email)){
                Task task = result;
                if(taskUpdateDTO.title() != null)task.setTitle(taskUpdateDTO.title());
                if(taskUpdateDTO.description() != null)task.setDescription(taskUpdateDTO.description());
                if(taskUpdateDTO.status() != null)task.setTaskStatus(taskUpdateDTO.status());
                taskRepository.save(task);
                TaskResponseDTO taskResponse = new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getTaskStatus(), task.getDate(), task.getUser().getName());
                return taskResponse;
            }else{
                throw new UnauthorizedException();
            }
            }
        public String deleteTaskById(Long id){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Task result = taskRepository.findById(id)
                    .orElseThrow(() -> new TaskNotFoundException(id));
            if(result.getUser().getEmail().equals(email)){
                taskRepository.delete(result);
                return "Task deletada com sucesso.";
            }else throw new UnauthorizedException();
            }
        }


        


