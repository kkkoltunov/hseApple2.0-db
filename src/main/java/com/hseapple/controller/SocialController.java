package com.hseapple.controller;

import com.hseapple.dao.ChatEntity;
import com.hseapple.dao.MessageEntity;
import com.hseapple.dao.PostEntity;
import com.hseapple.dao.UserEntity;
import com.hseapple.service.ChatService;
import com.hseapple.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(description = "Api to manage social",
        name = "Social Resource")
@SecurityRequirement(name = "Authorization")
public class SocialController {

    @Autowired
    ChatService chatService;

    @Autowired
    PostService postService;

    @Operation(summary = "Get group for course",
            description = "Provides group for course")
    @RequestMapping(value = "/group/{groupID}", method = RequestMethod.GET)
    @ResponseBody
    public ChatEntity getGroupForCourse(@PathVariable("groupID") Long groupID) {
        return chatService.getGroupForCourse(groupID);
    }

    @Operation(summary = "Delete group for course",
            description = "Delete the desired group for course. Access role - TEACHER")
    @DeleteMapping(value = "/group/{groupID}")
    public void deleteGroup(@PathVariable Long groupID) {
        chatService.deleteGroup(groupID);
    }

    @Operation(summary = "Get groups for course",
            description = "Provides all available groups list for course")
    @RequestMapping(value = "/course/{courseID}/group", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<ChatEntity> getGroups(@PathVariable Integer courseID) {
        return chatService.findAllGroups(courseID);
    }

    @Operation(summary = "Get message for chat",
            description = "Provides message for chat")
    @RequestMapping(value = "/group/{groupID}/message/{messageID}", method = RequestMethod.GET)
    @ResponseBody
    public MessageEntity getMessageForChat(@PathVariable("groupID") Long groupID,
                                           @PathVariable("messageID") Long messageID) {
        return chatService.getMessageForChat(groupID, messageID);
    }

    @Operation(summary = "Delete message",
            description = "Delete message for chat")
    @DeleteMapping(value = "/group/{groupID}/message/{messageID}")
    public void deleteMessage(@PathVariable Long groupID, @PathVariable Long messageID) {
        chatService.deleteMessage(groupID, messageID);
    }

    @Operation(summary = "Get messages for chat",
            description = "Provides all available messages for chat")
    @RequestMapping(value = "/group/{groupID}/message", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<MessageEntity> getMessages(@PathVariable Long groupID, @RequestParam("start") Long start) {
        return chatService.findMessages(groupID, start);
    }

    @Operation(summary = "Create message",
            description = "Creates new message")
    @RequestMapping(value = "/group/message", method = RequestMethod.POST)
    @ResponseBody
    public MessageEntity createMessage(@Valid @RequestBody MessageEntity messageEntity) {
        return chatService.createMessage(messageEntity);
    }

    @Operation(summary = "Delete member from Chat",
            description = "Delete member from chat")
    @DeleteMapping(value = "/group/{groupID}/member/{userID}")
    public void deleteMember(@PathVariable Long groupID, @PathVariable Long userID) {
        chatService.deleteMember(groupID, userID);
    }

    @Operation(summary = "Get chat members",
            description = "Provides all available chat members, that match id of chat")
    @RequestMapping(value = "/group/{groupID}/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<UserEntity> getListMembers(@PathVariable Long groupID) {
        return chatService.getMembers(groupID);
    }

    @Operation(summary = "Get chat member",
            description = "Provide chat member, that match id of chat and user")
    @RequestMapping(value = "/group/{groupID}/member/{userID}", method = RequestMethod.GET)
    @ResponseBody
    public UserEntity getMember(@PathVariable Long groupID, @PathVariable Long userID) {
        return chatService.getMember(groupID, userID);
    }

    @Operation(summary = "Get posts for course",
            description = "Provides all available posts for course. Access roles - TEACHER, ASSIST, STUDENT")
    @RequestMapping(value = "/course/{courseID}/post", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<PostEntity> getPosts(@PathVariable("courseID") Integer courseID, @RequestParam("start") Long start) {
        return postService.findAllPosts(courseID, start);
    }

    @Operation(summary = "Update post",
            description = "Provides new updated post. Access roles - TEACHER")
    @RequestMapping(value = "/course/post/{postID}", method = RequestMethod.PUT)
    @ResponseBody
    public PostEntity updatePost(@RequestBody PostEntity newPost, @PathVariable Long postID) {
        return postService.updatePost(newPost, postID);
    }

    @Operation(summary = "Create post",
            description = "Creates new post. Access roles - TEACHER")
    @RequestMapping(value = "/course/post", method = RequestMethod.POST)
    @ResponseBody
    public PostEntity createPost(@Valid @RequestBody PostEntity postEntity) {
        return postService.createPost(postEntity);

    }

    @Operation(summary = "Delete post",
            description = "Delete post. Access roles - TEACHER")
    @DeleteMapping(value = "course/post/{postID}")
    public void deletePost(@PathVariable Long postID) {
        postService.deletePost(postID);
    }

}