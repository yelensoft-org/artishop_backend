package com.yelensoft.artishop_backend.controllers;
import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.entities.Comment;
import com.yelensoft.artishop_backend.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add/{idUser}")
    public ResponseEntity<Object> addComment(@Valid @RequestBody Comment comment, @PathVariable Long idUser, @PathVariable(required = false) Long idStore, @PathVariable(required = false) Long idProduct){
        return ResponseHandler.generateResponse("success", HttpStatus.OK, commentService.addComment(comment, idUser, idStore, idProduct));
    }

    @GetMapping("/{id}")
        public Comment getCommentById(@PathVariable Long id) {
            return commentService.getCommentById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseHandler.generateResponse("success", HttpStatus.OK, null);
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<Object> getCommentsByStore(@PathVariable Long id) {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, commentService.getCommentsByStore(id));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getCommentsByProduct(@PathVariable Long id) {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, commentService.getCommentsByProduct(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getCommentsByUser(@PathVariable Long id) {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, commentService.getCommentsByUser(id));
    }

    @PutMapping("/update/{id}")
        public ResponseEntity<Object> updateComment(@PathVariable Long id, @Valid @RequestBody Comment comment) {
        return ResponseHandler.generateResponse("success", HttpStatus.OK, commentService.updateComment(id, comment));
    }
}
