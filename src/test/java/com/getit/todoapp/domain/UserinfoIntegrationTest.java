package com.getit.todoapp.domain;
import com.getit.todoapp.repository.UserRepository;
import com.getit.todoapp.service.UserService;
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

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
@RooIntegrationTest(entity = Userinfo.class)
public class UserinfoIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    UserinfoDataOnDemand dod;

	@Autowired
    UserService userService;

	@Autowired
    UserRepository userRepository;

	@Test
    public void testCountAllUserinfoes() {
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to initialize correctly", dod.getRandomUserinfo());
        long count = userService.countAllUserinfoes();
        Assert.assertTrue("Counter for 'Userinfo' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindUserinfo() {
        Userinfo obj = dod.getRandomUserinfo();
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to provide an identifier", id);
        obj = userService.findUserinfo(id);
        Assert.assertNotNull("Find method for 'Userinfo' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Userinfo' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllUserinfoes() {
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to initialize correctly", dod.getRandomUserinfo());
        long count = userService.countAllUserinfoes();
        Assert.assertTrue("Too expensive to perform a find all test for 'Userinfo', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Userinfo> result = userService.findAllUserinfoes();
        Assert.assertNotNull("Find all method for 'Userinfo' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Userinfo' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindUserinfoEntries() {
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to initialize correctly", dod.getRandomUserinfo());
        long count = userService.countAllUserinfoes();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Userinfo> result = userService.findUserinfoEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Userinfo' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Userinfo' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Userinfo obj = dod.getRandomUserinfo();
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to provide an identifier", id);
        obj = userService.findUserinfo(id);
        Assert.assertNotNull("Find method for 'Userinfo' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyUserinfo(obj);
        Integer currentVersion = obj.getVersion();
        userRepository.flush();
        Assert.assertTrue("Version for 'Userinfo' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testUpdateUserinfoUpdate() {
        Userinfo obj = dod.getRandomUserinfo();
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to provide an identifier", id);
        obj = userService.findUserinfo(id);
        boolean modified =  dod.modifyUserinfo(obj);
        Integer currentVersion = obj.getVersion();
        Userinfo merged = userService.updateUserinfo(obj);
        userRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Userinfo' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveUserinfo() {
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to initialize correctly", dod.getRandomUserinfo());
        Userinfo obj = dod.getNewTransientUserinfo(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Userinfo' identifier to be null", obj.getId());
        try {
            userService.saveUserinfo(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        userRepository.flush();
        Assert.assertNotNull("Expected 'Userinfo' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDeleteUserinfo() {
        Userinfo obj = dod.getRandomUserinfo();
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Userinfo' failed to provide an identifier", id);
        obj = userService.findUserinfo(id);
        userService.deleteUserinfo(obj);
        userRepository.flush();
        Assert.assertNull("Failed to remove 'Userinfo' with identifier '" + id + "'", userService.findUserinfo(id));
    }
}
