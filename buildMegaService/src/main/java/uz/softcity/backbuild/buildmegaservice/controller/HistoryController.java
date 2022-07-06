package uz.softcity.backbuild.buildmegaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.softcity.backbuild.buildmegaservice.entity.History;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.HistoryDto;
import uz.softcity.backbuild.buildmegaservice.service.HistoryService;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    @Autowired
    HistoryService historyService;

    /**
     *
     * @return success and all object
     */
    @GetMapping()
    public HttpEntity<?> getAllHistory() {
        return ResponseEntity.ok(historyService.getAllHistory());
    }

    /**
     *
     * @param id searching object id
     * @return - when found success and object otherwise conflict and empty body
     */
    @GetMapping("/{id}")
    public HttpEntity<?> getHistoryById(@PathVariable long id) {
        History historyById = historyService.getHistoryById(id);
        return ResponseEntity.status(historyById != null ? 200 : 409).body(historyById);
    }

    /**
     *
     * @param historyDto - for create new object
     * @return - when added success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('ADD_HISTORY')")
    @PostMapping
    public HttpEntity<?> addHistory(@RequestBody HistoryDto historyDto) {
        ApiResponse apiResponse = historyService.addHistory(historyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     *
     * @param id - old object id
     * @param contact - new object fo update
     * @return - when edited success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('EDIT_HISTORY')")
    @PutMapping("/{id}")
    public HttpEntity<?> editHistory(@PathVariable long id, @RequestBody HistoryDto historyDto) {
        ApiResponse apiResponse = historyService.editHistory(id, historyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     *
     * @param id - deleted object id
     * @return - when deleted success otherwise conflict
     */
    @PreAuthorize(value = "hasAuthority('DELETE_HISTORY')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteHistory(@PathVariable int id) {
        ApiResponse apiResponse = historyService.deleteHistory(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }
}
