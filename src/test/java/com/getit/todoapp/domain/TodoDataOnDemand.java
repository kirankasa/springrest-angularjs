package com.getit.todoapp.domain;
import com.getit.todoapp.repository.TodoRepository;
import com.getit.todoapp.service.TodoService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Todo.class)
public class TodoDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Todo> data;

	@Autowired
    UserinfoDataOnDemand userinfoDataOnDemand;

	@Autowired
    TodoService todoService;

	@Autowired
    TodoRepository todoRepository;

	public Todo getNewTransientTodo(int index) {
        Todo obj = new Todo();
        setIsCompleted(obj, index);
        setName(obj, index);
        setPriority(obj, index);
        setTargetDate(obj, index);
        return obj;
    }

	public void setIsCompleted(Todo obj, int index) {
        Boolean isCompleted = Boolean.TRUE;
        obj.setIsCompleted(isCompleted);
    }

	public void setName(Todo obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public void setPriority(Todo obj, int index) {
        Priority priority = Priority.class.getEnumConstants()[0];
        obj.setPriority(priority);
    }

	public void setTargetDate(Todo obj, int index) {
        Date targetDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setTargetDate(targetDate);
    }

	public Todo getSpecificTodo(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Todo obj = data.get(index);
        Long id = obj.getId();
        return todoService.findTodo(id);
    }

	public Todo getRandomTodo() {
        init();
        Todo obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return todoService.findTodo(id);
    }

	public boolean modifyTodo(Todo obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = todoService.findTodoEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Todo' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Todo>();
        for (int i = 0; i < 10; i++) {
            Todo obj = getNewTransientTodo(i);
            try {
                todoService.saveTodo(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            todoRepository.flush();
            data.add(obj);
        }
    }
}
