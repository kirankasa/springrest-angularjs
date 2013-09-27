package com.getit.todoapp.domain;
import com.getit.todoapp.repository.TodoRepository;
import com.getit.todoapp.service.TodoService;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
@RooIntegrationTest(entity = Todo.class)
public class TodoIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    TodoDataOnDemand dod;

	@Autowired
    TodoService todoService;

	@Autowired
    TodoRepository todoRepository;

	@Test
    public void testCountAllTodoes() {
        Assert.assertNotNull("Data on demand for 'Todo' failed to initialize correctly", dod.getRandomTodo());
        long count = todoService.countAllTodoes();
        Assert.assertTrue("Counter for 'Todo' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindTodo() {
        Todo obj = dod.getRandomTodo();
        Assert.assertNotNull("Data on demand for 'Todo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Todo' failed to provide an identifier", id);
        obj = todoService.findTodo(id);
        Assert.assertNotNull("Find method for 'Todo' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Todo' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllTodoes() {
        Assert.assertNotNull("Data on demand for 'Todo' failed to initialize correctly", dod.getRandomTodo());
        long count = todoService.countAllTodoes();
        Assert.assertTrue("Too expensive to perform a find all test for 'Todo', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Todo> result = todoService.findAllTodoes();
        Assert.assertNotNull("Find all method for 'Todo' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Todo' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindTodoEntries() {
        Assert.assertNotNull("Data on demand for 'Todo' failed to initialize correctly", dod.getRandomTodo());
        long count = todoService.countAllTodoes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Todo> result = todoService.findTodoEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Todo' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Todo' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Todo obj = dod.getRandomTodo();
        Assert.assertNotNull("Data on demand for 'Todo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Todo' failed to provide an identifier", id);
        obj = todoService.findTodo(id);
        Assert.assertNotNull("Find method for 'Todo' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyTodo(obj);
        Integer currentVersion = obj.getVersion();
        todoRepository.flush();
        Assert.assertTrue("Version for 'Todo' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateTodoUpdate() {
        Todo obj = dod.getRandomTodo();
        Assert.assertNotNull("Data on demand for 'Todo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Todo' failed to provide an identifier", id);
        obj = todoService.findTodo(id);
        boolean modified =  dod.modifyTodo(obj);
        Integer currentVersion = obj.getVersion();
        Todo merged = todoService.updateTodo(obj);
        todoRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Todo' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveTodo() {
        Assert.assertNotNull("Data on demand for 'Todo' failed to initialize correctly", dod.getRandomTodo());
        Todo obj = dod.getNewTransientTodo(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Todo' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Todo' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Todo' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteTodo() {
        Todo obj = dod.getRandomTodo();
        Assert.assertNotNull("Data on demand for 'Todo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Todo' failed to provide an identifier", id);
        obj = todoService.findTodo(id);
        todoService.deleteTodo(obj);
        todoRepository.flush();
        Assert.assertNull("Failed to remove 'Todo' with identifier '" + id + "'", todoService.findTodo(id));
    }
}
