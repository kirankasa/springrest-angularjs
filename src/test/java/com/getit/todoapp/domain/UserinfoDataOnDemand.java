package com.getit.todoapp.domain;
import com.getit.todoapp.repository.UserRepository;
import com.getit.todoapp.service.UserService;
import java.security.SecureRandom;
import java.util.ArrayList;
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
@RooDataOnDemand(entity = Userinfo.class)
public class UserinfoDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Userinfo> data;

	@Autowired
    UserService userService;

	@Autowired
    UserRepository userRepository;

	public Userinfo getNewTransientUserinfo(int index) {
        Userinfo obj = new Userinfo();
        setEmail(obj, index);
        setFirstName(obj, index);
        setLastName(obj, index);
        setPassword(obj, index);
        setUserName(obj, index);
        return obj;
    }

	public void setEmail(Userinfo obj, int index) {
        String email = "foo" + index + "@bar.com";
        obj.setEmail(email);
    }

	public void setFirstName(Userinfo obj, int index) {
        String firstName = "firstName_" + index;
        obj.setFirstName(firstName);
    }

	public void setLastName(Userinfo obj, int index) {
        String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }

	public void setPassword(Userinfo obj, int index) {
        String password = "password_" + index;
        obj.setPassword(password);
    }

	public void setUserName(Userinfo obj, int index) {
        String userName = "userName_" + index;
        obj.setUserName(userName);
    }

	public Userinfo getSpecificUserinfo(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Userinfo obj = data.get(index);
        Long id = obj.getId();
        return userService.findUserinfo(id);
    }

	public Userinfo getRandomUserinfo() {
        init();
        Userinfo obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return userService.findUserinfo(id);
    }

	public boolean modifyUserinfo(Userinfo obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = userService.findUserinfoEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Userinfo' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Userinfo>();
        for (int i = 0; i < 10; i++) {
            Userinfo obj = getNewTransientUserinfo(i);
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
            data.add(obj);
        }
    }
}
