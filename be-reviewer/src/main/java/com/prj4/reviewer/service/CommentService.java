package com.prj4.reviewer.service;

import com.prj4.reviewer.entity.Comment;
import com.prj4.reviewer.entity.Company;
import com.prj4.reviewer.entity.Reviewer;
import com.prj4.reviewer.reporsitory.AdminRepository;
import com.prj4.reviewer.reporsitory.CommentRepository;
import com.prj4.reviewer.reporsitory.CompanyRepository;
import com.prj4.reviewer.reporsitory.ReviewerRepository;
import com.prj4.reviewer.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReviewerRepository reviewerRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ImageService imageService;

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<CommentResponse> getCommentByProductId(String idProduct){
        List<Comment> lstOriginalComment = commentRepository.findByIdProduct(idProduct);
        List<CommentResponse> lstResult = new ArrayList<>();
        String fullName = null;
        String imgAvar = null;
        for(Comment c: lstOriginalComment) {
            if (c.getRoleUser().equals("ROLE_NORMAL")) {
                fullName = reviewerRepository.findByIdReviewer(c.getIdReviewer()).getFullName();
                String imageId = reviewerRepository.findByIdReviewer(c.getIdReviewer()).getImgAvatar();
                imgAvar = imageService.getImagePathById(imageId);
            } else if (c.getRoleUser().equals("ROLE_COMPANY")) {
                fullName = companyRepository.findByIdCompany(c.getIdReviewer()).getNameCompany();
                String imageId = companyRepository.findByIdCompany(c.getIdReviewer()).getImgAvatarCompany();
                imgAvar = imageService.getImagePathById(imageId);
            } else {
                fullName = adminRepository.findByIdAdmin(c.getIdReviewer()).getFullNameAdmin();
                imgAvar = "http://localhost/img/reviewer/avar-pay.png";
            }
            CommentResponse commentResponse = new CommentResponse(c.getContent(), c.getDateCreate(), c.getIdComment(),
                    idProduct, c.getIdReply(), c.getIdReviewer(), c.getReply(), imgAvar, fullName);
            lstResult.add(commentResponse);
        }
        return lstResult;
    }
}
