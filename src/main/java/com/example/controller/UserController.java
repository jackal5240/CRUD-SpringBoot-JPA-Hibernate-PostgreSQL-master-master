package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.page.AjaxResponse;
import com.example.page.Response;
import com.example.page.Status;
import com.example.repository.UserRepository;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/user")
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "Post", notes = "Create User No.")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response create(@RequestBody User user) {
    	logger.info("Post Create User No. Start ...");
        userRepository.save(user);
        return new AjaxResponse(Status.SUCCESS, "", null);
    }

    @ApiOperation(value = "Get", notes = "Get User No.")
//    @RequestMapping(value = "/{id}")
    @GetMapping(value = "/{id}")
    public User read(@PathVariable long id) {
    	logger.info("Get User No. Start ...");
        return userRepository.findOne(id);
    }

    @ApiOperation(value = "Put", notes = "Update User No.")
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response update(@RequestBody User user) {
    	logger.info("Put Update User No. Start ...");
        userRepository.save(user);
        return new AjaxResponse(Status.SUCCESS, "", null);
    }

    @ApiOperation(value = "Delete", notes = "Delete User No.")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable long id) {
    	logger.info("Delete User No. Start ...");
        userRepository.delete(id);
        return new AjaxResponse(Status.SUCCESS, "", null);
    }

}
