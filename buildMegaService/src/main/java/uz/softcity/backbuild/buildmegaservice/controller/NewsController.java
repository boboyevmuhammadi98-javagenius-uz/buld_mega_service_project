package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.News;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.NewsDto;
import uz.softcity.backbuild.buildmegaservice.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    /**
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    /**
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getNewsById(@PathVariable long id) {
        News newsById = newsService.getNewsById(id);
        return ResponseEntity.status(newsById != null ? 200 : 409).body(newsById);
    }

    /**
     * @param newsDto - for create new object
     * @return - when added success otherwise conflict
     */
    @PostMapping
    @PreAuthorize(value = "hasAuthority('ADD_NEWS')")
    public HttpEntity<?> addNews(@RequestBody NewsDto newsDto) {
        ApiResponse apiResponse = newsService.addNews(newsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id      - old object id
     * @param newsDto - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_NEWS')")
    @PutMapping("/{id}")
    public HttpEntity<?> editNews(@PathVariable long id, @RequestBody NewsDto newsDto) {
        ApiResponse apiResponse = newsService.editNews(id, newsDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_NEWS')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteNews(@PathVariable int id) {
        ApiResponse apiResponse = newsService.deleteNews(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
