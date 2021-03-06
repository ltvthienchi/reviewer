package com.prj4.reviewer.controller;

import com.prj4.reviewer.core.Constants;
import com.prj4.reviewer.core.JsonResponse;
import com.prj4.reviewer.core.SortByDate;
import com.prj4.reviewer.entity.*;
import com.prj4.reviewer.reporsitory.ReviewerRepository;
import com.prj4.reviewer.request.*;
import com.prj4.reviewer.response.*;
import com.prj4.reviewer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewerRestController {

    private final static String BASE_POST_LINK = "/data/reviewer/";

    @Autowired
    PostService postService;

    @Autowired
    ReviewerService reviewerService;

    @Autowired
    ReviewerRepository reviewerRepository;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    HistoryService historyService;

    @PostMapping(BASE_POST_LINK + "feedbackCompany")
    public FeedbackCompanyResponse feedbackCompany(@RequestBody @Valid FeedbackCompanyRequest feedbackCompanyRequest) {
        FeedbackCompanyResponse feedbackCompanyResponse = reviewerService.feedbackCompany(feedbackCompanyRequest);
        return feedbackCompanyResponse;
    }

    @GetMapping(BASE_POST_LINK + "reviewers")
    List<Reviewer> getReviewers() {
        return (List<Reviewer>) reviewerRepository.findAll();
    }

    @PostMapping(BASE_POST_LINK + "updateReview")
    public JsonResponse<String> updateReviewer(
            @RequestParam("idReviewer") String idReviewer,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dob") String dob,
            @RequestParam("gender") String gender,
            @RequestParam(value = "avaReviewer", required = false) MultipartFile avaReviewer,
            @RequestParam(value = "panelReviewer", required = false) MultipartFile panelReviewer
            ) {

        try {
            Date newDob = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
            int genderReviewer = gender.equals("true") ? 1 : 0;
            reviewerService.updateInfo(idReviewer, firstName, lastName, newDob, genderReviewer, avaReviewer, panelReviewer);
            return JsonResponse.accept("Success");
        } catch (Exception ex) {
            return JsonResponse.reject(ex.getMessage());
        }
    }

    @PostMapping(BASE_POST_LINK + "getReviewerInfoById")
    ReviewerResponse getReviewerInfoById(@RequestBody String idReviewer) {
        return reviewerService.getReviewerInfoById(idReviewer);
    }

    @PostMapping(BASE_POST_LINK + "getReviewerInfo")
    ReviewerInfoResponse getReviewerInfo(@RequestBody ReviewerInfoRequest reviewerInfoRequest) {
        return reviewerService.getReviewerInfo(reviewerInfoRequest.getEmail(), reviewerInfoRequest.getRole());
    }

    @PostMapping(BASE_POST_LINK + "updatePassword")
    public JsonResponse<String> updatePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        String encodedNewPass = new BCryptPasswordEncoder().encode(changePasswordRequest.getNewPassword());
        String email = changePasswordRequest.getEmail();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userService.findOne(email);
        if (encoder.matches(changePasswordRequest.getOldPassword(), user.getPassAccount())) {
            try {
                user.setPassAccount(encodedNewPass);
                userService.save(user);
                return JsonResponse.accept("Success");
            } catch (Exception ex) {
                return JsonResponse.reject(ex.getMessage());
            }
        } else {
            return JsonResponse.reject("Current password is wrong!!");
        }
    }

    @PostMapping(BASE_POST_LINK + "getAllPostIsFollow")
    public List<PostResponse> getAllPostIsFollow(@RequestBody String idReviewer) {
        List<PostResponse> lst = reviewerService.getAllPostIsFollowed(idReviewer);
        Collections.sort(lst, new SortByDate());
        Collections.sort(lst, Collections.reverseOrder());
        return lst;
    }

    @PostMapping(BASE_POST_LINK + "reviewComp")
    public JsonResponse<String> reviewerComp(@RequestBody ReviewCompRequest reviewCompRequest) {
        try {
            reviewerService.createRatingComp(reviewCompRequest);
            historyService.createReviewCompHistory(reviewCompRequest.getIdReviewer(), reviewCompRequest.getIdCompany(), 1);
            return JsonResponse.accept("success");
        } catch (Exception ex) {
            return JsonResponse.reject(ex.getMessage());
        }
    }

    @PostMapping(BASE_POST_LINK + "getReviewComp")
    public List<ReviewCompanyResponse> getReviewComp(@RequestBody String idCompany) {
        return reviewerService.getListReviewComp(idCompany);

    }

    @PostMapping(BASE_POST_LINK + "getReviewCompByIdReviewer")
    public ReviewCompany getReviewCompByIdReviewer(@RequestBody ReviewCompanyRequest reviewCompanyRequest) {
        return reviewerService.getReviewCompByIdReviewer(reviewCompanyRequest.getIdReviewer(),
                reviewCompanyRequest.getIdCompany());
    }

    @PostMapping(BASE_POST_LINK + "getHistoryActivity")
    public List<ActivityHistory> getHistoryActivity(@RequestBody String idReviewer) {

        return historyService.getActivityHistory(idReviewer);
    }


}
