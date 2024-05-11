package com.yelensoft.artishop_backend.services;
import com.yelensoft.artishop_backend.entities.Comment;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    public Object addComment(@Valid Comment comment, Long userId, Long storeId, Long productId) {
        UserApp user = userService.getUserById(userId);
        if (user != null) {
            comment.setUserApp(user);
        } else {
            throw new RuntimeException("User with id " + userId + " does not exist");
        }

        if (storeId != null && storeId.NotBlank()) {
            Store store = storeService.getStoreById(storeId);
            if (store != null) {
                comment.setStore(store);
            } else {
                throw new RuntimeException("Store with id " + storeId + " does not exist");
            }
        }

        if (productId != null) {
            Product product = productService.getProductById(productId);
            if (product != null) {
                comment.setProduct(product);
            } else {
                throw new RuntimeException("Product with id " + productId + " does not exist");
            }
        }

        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> getCommentsByStore(Long storeId) {
        return commentRepository.findByStoreId(storeId);
    }

    public List<Comment> getCommentsByProduct(Long productId) {
        return commentRepository.findByProductId(productId);
    }

    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByUserAppId(userId);
    }

    public Comment updateComment(Long id, Comment comment) {
        Comment existingComment = commentRepository.findById(id).orElse(null);
        if (existingComment != null) {
            existingComment.setText(comment.getText());
            existingComment.setStore(comment.getStore());
            existingComment.setProduct(comment.getProduct());
            existingComment.setUserApp(comment.getUserApp());
            return commentRepository.save(existingComment);
        } else {
            throw new RuntimeException("Comment with id " + id + " does not exist");
        }
    }
}
