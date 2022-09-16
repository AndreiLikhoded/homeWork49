package com.example.homeWork49.controller;

import com.example.homeWork49.labjob.Candidate;
import com.example.homeWork49.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class CandidateController {

    private boolean countVote = true;
    private final Candidate anon = new Candidate(0, "", "anon.jpeg", 0);

    @RequestMapping(value = "/candidates")
    public String allCandidates(Model model) {
        model.addAttribute("candidates", getCandidate());
        return "candidates";
    }

    private List<Candidate> getCandidate(){
        List<Candidate> candidates = FileService.readString();
        AtomicReference<Integer> i = new AtomicReference<>(1);
        candidates.forEach(candidate -> {
            candidate.setId(i.get());
            i.getAndSet(i.get() + 1);
        });
        return candidates;
    }

    @RequestMapping(value = "/thanksPath")
    public String tanksPage(int id, Model model) {
        Candidate candidate = null;
        List<Candidate> candidates =  getCandidate();
        for (Candidate candidate1 : candidates) {
            if (candidate1.getId() == id) {
                candidate = candidate1;
                int vote = candidate1.getVotes();
                candidate1.setVotes(vote + 1);
                break;
            }
        }

        if (candidate != null) {
            int votesPercent = countVotes(candidate.getVotes());
            candidate.setPercent(votesPercent);
            model.addAttribute("user", candidate);
        } else {
            model.addAttribute("user", anon);

        }
        countVote = false;
        return "thanksTemplate";
    }

//    @RequestMapping(value = "/votes")
//    public String allVotes(Model model) {
//        model.addAttribute("votes", votes);
//        return "votes";
//    }

    private int countVotes(int candidateVotes) {

        int totalVotes = 1;

        for (Candidate candidate :  getCandidate()) {

            totalVotes = totalVotes + candidate.getVotes();
        }

        return (100 * candidateVotes) / totalVotes;
    }


}
