package com.translator.hub.models.DTO;
//
//import com.translator.hub.models.User;
//import com.translator.hub.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
//import org.springframework.validation.Validator;
//
//
//@Component
//public class UserValidator implements Validator {
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return User.class.equals(aClass);
//    }
//
//    @Override
//    public void validate(Object o, Errors errors) {
//        User user = (User) o;
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
//        if (user.getFirstName().length() < 3 || user.getFirstName().length() > 10) {
//            errors.rejectValue("firstName", "Size.user.firsName");
//        }
//        if (user.getLastName().length() < 3 || user.getLastName().length() > 10) {
//            errors.rejectValue("lastName", "Size.user.lastName");
//        }
//        if (userService.findUserByEmail(user.getEmail()) != null) {
//            errors.rejectValue("email", "Duplicate.user.email");
//        }
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
//        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
//            errors.rejectValue("password", "Size.user.password");
//        }
//
//        if (!user.getPasswordConfirm().equals(user.getPassword())) {
//            errors.rejectValue("passwordConfirm", "Diff.user.passwordConfirm");
//        }
//    }
//}