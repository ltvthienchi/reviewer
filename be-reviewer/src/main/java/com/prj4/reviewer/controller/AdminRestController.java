package com.prj4.reviewer.controller;

import com.prj4.reviewer.core.JsonResponse;
import com.prj4.reviewer.entity.Admin;
import com.prj4.reviewer.entity.Comment;
import com.prj4.reviewer.entity.Post;
import com.prj4.reviewer.request.AdminBlockRequest;
import com.prj4.reviewer.request.AdminRequest;
import com.prj4.reviewer.request.AdminResetPass;
import com.prj4.reviewer.service.AdminService;
import com.prj4.reviewer.service.GenerateId;
import com.prj4.reviewer.response.CommentReported;
import com.prj4.reviewer.response.CommentResponse;
import com.prj4.reviewer.response.PostResponse;
import com.prj4.reviewer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminRestController {
    private final static String BASE_POST_LINK = "/data/admin/";
    @Autowired
    AdminService adminService;
    @Autowired
    GenerateId generateId;
    @Autowired
    PostService postService;
    @Autowired
    CreatePostResponseService createPostResponseService;
    @Autowired
    CommentService commentService;

    @PostMapping(BASE_POST_LINK + "createAdmin")
    public JsonResponse<String> createAdmin(@RequestBody @Valid AdminRequest adminRequest) {

        String idAdmin = generateId.generateId("ADMIN_", new Date());
        String encodedPass = new BCryptPasswordEncoder().encode(adminRequest.getPassAdmin());
        Admin admin = new Admin(idAdmin, adminRequest.getFullNameAdmin() ,adminRequest.getEmailAdmin(),encodedPass,adminRequest.getDobAdmin(),
                new Date(),true,adminRequest.getAddressAdmin(),adminRequest.getPhoneAdmin(), "1");
        adminService.save(admin);
        return JsonResponse.accept("");
    }

    @GetMapping(BASE_POST_LINK + "getAllAdmin")
    public List<Admin> getAllAdmin(){

        return adminService.findAllAdmin();
    }

    @PostMapping(BASE_POST_LINK + "editAdmin")
    public JsonResponse<String> editAdmin(@RequestBody @Valid AdminRequest adminRequest){

        Admin admin = adminService.findByIdAdmin(adminRequest.getIdAdmin());
        // admin.setActive(adminRequest.isActive());
        admin.setDobAdmin(adminRequest.getDobAdmin());
        admin.setEmailAdmin(adminRequest.getEmailAdmin());
        admin.setFullNameAdmin(adminRequest.getFullNameAdmin());
       // String encodedPass = new BCryptPasswordEncoder().encode(adminRequest.getPassAdmin());
       // admin.setPassAdmin(encodedPass);
        admin.setAddressAdmin(adminRequest.getAddressAdmin());
        admin.setPhoneAdmin(adminRequest.getPhoneAdmin());
        adminService.editAdmin(admin);


        return JsonResponse.accept("");
    }

    @PostMapping(BASE_POST_LINK + "lockAdmin")
    public JsonResponse<String> lockAdmin(@RequestBody @Valid AdminBlockRequest adminBlockRequest){
        Admin admin = adminService.findByIdAdmin(adminBlockRequest.getIdAdmin());
        Boolean a = Boolean.valueOf(adminBlockRequest.getIsActive());
        admin.setActive(!a);
        adminService.save(admin);
        return JsonResponse.accept("");
    }

    @PostMapping(BASE_POST_LINK + "getRole")
    public String getRole(@RequestBody @Valid AdminBlockRequest adminBlockRequest){
        Admin admin = adminService.findByIdAdmin(adminBlockRequest.getIdAdmin());
        return admin.getRoleAdmin();
    }

    @PostMapping(BASE_POST_LINK + "resetPassAdmin")
    public JsonResponse<String> resetPassAdmin(@RequestBody @Valid AdminResetPass adminResetPass){
        Admin admin = adminService.findByIdAdmin(adminResetPass.getIdAdmin());
        String encodedPass = new BCryptPasswordEncoder().encode(adminResetPass.getPassAdmin());
        admin.setPassAdmin(encodedPass);
        adminService.save(admin);
        return JsonResponse.accept("");
    }


    @GetMapping(BASE_POST_LINK + "getAllProduct")
    public List<PostResponse> getAllProduct() {
        List<Post> lstPost = postService.getAllPostByDtCreated();
        return createPostResponseService.createListPostReponse(lstPost);
    }

    @GetMapping(BASE_POST_LINK + "getAllCommentReported")
    public List<CommentReported> getAllCommentReported() {
        List<CommentReported> lstCommentReported = new ArrayList<>();
        return commentService.getAllCommentReported();
    }

    @PostMapping(BASE_POST_LINK + "deleteComment")
    public JsonResponse<String> deleteComment(@RequestBody String idComment) {
        try {
            commentService.deleteComment(idComment);
            return JsonResponse.accept("success");
        } catch(Exception ex) {
            return JsonResponse.reject(ex.getMessage());
        }

    }


}
